package com.revenuecat.purchases.kmp.buildlogic.swift.task

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Verifies that precompiled Swift artifacts exist in [outputDirectory].
 *
 * Used when [skipSwiftBuild][com.revenuecat.purchases.kmp.buildlogic.swift.shouldSkipSwiftBuild]
 * is enabled so Kotlin/Native compilation can consume artifacts produced on a separate CI job
 * or machine.
 *
 * This task has no outputs; `outputs.upToDateWhen { true }` keeps Gradle from re-running it
 * once the first validation in a build has succeeded.
 */
abstract class ValidatePrecompiledSwiftArtifactsTask : DefaultTask() {

    init {
        outputs.upToDateWhen { true }
    }

    @get:Input
    abstract val libraryName: Property<String>

    @get:Input
    abstract val headerName: Property<String>

    @get:Input
    abstract val outputDirectory: Property<String>

    @TaskAction
    fun validate() {
        val directory = File(outputDirectory.get())
        val library = directory.resolve(libraryName.get())
        val header = directory.resolve(headerName.get())
        val moduleMap = directory.resolve("module.modulemap")

        check(library.isFile) {
            "Missing precompiled Swift library at ${library.absolutePath}. " +
                "Build Swift artifacts first (e.g. ./gradlew compileSwiftIosArtifacts " +
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
