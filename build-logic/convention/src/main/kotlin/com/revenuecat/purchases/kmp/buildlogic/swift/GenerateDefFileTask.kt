package com.revenuecat.purchases.kmp.buildlogic.swift

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

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

    /** The output .def file */
    @get:OutputFile
    abstract val defFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val file = defFile.get().asFile
        file.parentFile.mkdirs()

        val toolchain = toolchainPath.get()

        val baseContent = """
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
}
