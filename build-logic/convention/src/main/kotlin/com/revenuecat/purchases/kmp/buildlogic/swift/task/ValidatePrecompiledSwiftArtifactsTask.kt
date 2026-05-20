package com.revenuecat.purchases.kmp.buildlogic.swift.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

/**
 * Verifies that precompiled Swift artifacts exist in [outputDir].
 *
 * Used when [skipSwiftBuild][com.revenuecat.purchases.kmp.buildlogic.swift.shouldSkipSwiftBuild]
 * is enabled so Kotlin/Native compilation can consume artifacts produced on a separate CI job
 * or machine.
 */
abstract class ValidatePrecompiledSwiftArtifactsTask : DefaultTask() {

    @get:Input
    abstract val libraryName: Property<String>

    @get:Input
    abstract val headerName: Property<String>

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun validate() {
        val directory = outputDir.get().asFile
        val library = directory.resolve(libraryName.get())
        val header = directory.resolve(headerName.get())
        val moduleMap = directory.resolve("module.modulemap")

        check(library.isFile) {
            "Missing precompiled Swift library at ${library.absolutePath}. " +
                "Build Swift artifacts first (e.g. ./gradlew compilePrecompiledSwiftIosArtifacts " +
                "with -PswiftConfiguration=release) or disable -PskipSwiftBuild."
        }
        check(header.isFile) {
            "Missing precompiled Swift header at ${header.absolutePath}. " +
                "Build Swift artifacts first or disable -PskipSwiftBuild."
        }
        check(moduleMap.isFile) {
            "Missing precompiled Swift module map at ${moduleMap.absolutePath}. " +
                "Build Swift artifacts first or disable -PskipSwiftBuild."
        }
    }
}
