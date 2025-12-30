package com.revenuecat.purchases.kmp.buildlogic.swift.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.security.MessageDigest

/**
 * Gradle task that generates a cinterop .def file for a Swift package.
 *
 * The .def file tells Kotlin/Native cinterop how to process the Swift-generated
 * Objective-C header and link against the static library.
 */
abstract class GenerateDefFileTask : DefaultTask() {

    /** The Kotlin package name for the generated bindings */
    @get:Input
    abstract val packageName: Property<String>

    /** The module name (used in the `modules` directive) */
    @get:Input
    abstract val moduleName: Property<String>

    /** The static library name (e.g., "libRevenueCat.a") */
    @get:Input
    abstract val libraryName: Property<String>

    /** Optional custom declarations to force cinterop binding generation */
    @get:Input
    @get:Optional
    abstract val customDeclarations: Property<String>

    /** The Xcode toolchain path */
    @get:Input
    abstract val toolchainPath: Property<String>

    /** The Swift source directory - used to compute a hash for cache invalidation */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val swiftSourceDir: DirectoryProperty

    /** The output .def file */
    @get:OutputFile
    abstract val defFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val file = defFile.get().asFile
        file.parentFile.mkdirs()

        val toolchain = toolchainPath.get()
        // We add a hash of the Swift source directory to force cinterop to re-run when
        // Swift source changes, even if the changes are non-public. This is necessary
        // because we found using inputs.file() on the cinterop task directly causes
        // failures when building debug after release (e.g. publishToMavenLocal). This
        // seems related to the cinterop commonizer.
        val sourceHash = computeSourceHash()

        val baseContent = """
            # sourceHash=$sourceHash
            language = Objective-C
            package = ${packageName.get()}
            modules = ${moduleName.get()}
            staticLibraries = ${libraryName.get()}
            linkerOpts = -L/usr/lib/swift
            linkerOpts.ios_x64 = -L$toolchain/lib/swift/iphonesimulator/
            linkerOpts.ios_arm64 = -L$toolchain/lib/swift/iphoneos/
            linkerOpts.ios_simulator_arm64 = -L$toolchain/lib/swift/iphonesimulator/
        """.trimIndent()

        val content = if (customDeclarations.isPresent) {
            "$baseContent\n---\n\n${customDeclarations.get()}"
        } else {
            baseContent
        }

        file.writeText(content)
    }

    private fun computeSourceHash(): String {
        val sourceDir = swiftSourceDir.get().asFile
        val digest = MessageDigest.getInstance("MD5")

        sourceDir.walkTopDown()
            .filter { it.isFile && it.extension == "swift" }
            .sortedBy { it.relativeTo(sourceDir).path }
            .forEach { file ->
                digest.update(file.relativeTo(sourceDir).path.toByteArray())
                digest.update(file.readBytes())
            }

        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}
