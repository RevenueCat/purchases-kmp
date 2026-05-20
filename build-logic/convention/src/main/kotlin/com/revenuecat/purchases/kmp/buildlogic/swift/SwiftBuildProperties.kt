package com.revenuecat.purchases.kmp.buildlogic.swift

import org.gradle.api.Project

private const val COMPILE_SWIFT_IOS_ARTIFACTS_REGISTERED_KEY =
    "revenuecat.compileSwiftIosArtifactsRegistered"

internal fun Project.shouldSkipSwiftBuild(): Boolean =
    findProperty("skipSwiftBuild")?.toString()?.toBooleanStrictOrNull() == true

/**
 * Optional override for the Xcode used to compile Swift static libraries.
 *
 * Intended for local development when multiple Xcode versions are installed (e.g.
 * `xcode-select` points at 26.x but Swift artifacts should be built with 16.4).
 * CI uses dedicated executors instead (`xcode16` vs `xcode26`).
 */
internal fun Project.getSwiftDeveloperDir(): String? =
    findProperty("swiftDeveloperDir")?.toString()?.takeIf { it.isNotBlank() }
        ?: providers.environmentVariable("SWIFT_DEVELOPER_DIR").orNull?.takeIf { it.isNotBlank() }

internal fun Project.ensureCompileSwiftIosArtifactsAggregateTask() {
    if (rootProject.extensions.extraProperties.has(COMPILE_SWIFT_IOS_ARTIFACTS_REGISTERED_KEY)) {
        return
    }
    rootProject.extensions.extraProperties.set(COMPILE_SWIFT_IOS_ARTIFACTS_REGISTERED_KEY, true)

    gradle.projectsEvaluated {
        rootProject.tasks.register("compileSwiftIosArtifacts") {
            val aggregate = this
            group = "build"
            description = "Compiles Swift static libraries for all iOS Kotlin/Native targets."
            val iosSwiftTaskSuffixes = listOf("IosArm64", "IosSimulatorArm64", "IosX64")
            rootProject.subprojects.forEach { subproject ->
                subproject.tasks.names
                    .filter { taskName ->
                        taskName.startsWith("compileSwift") &&
                            iosSwiftTaskSuffixes.any { suffix -> taskName.endsWith(suffix) }
                    }
                    .forEach { taskName -> aggregate.dependsOn(subproject.tasks.named(taskName)) }
            }
        }
    }
}
