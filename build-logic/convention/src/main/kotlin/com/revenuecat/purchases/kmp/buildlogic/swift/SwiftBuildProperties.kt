package com.revenuecat.purchases.kmp.buildlogic.swift

import org.gradle.api.Project

private const val COMPILE_SWIFT_IOS_ARTIFACTS_REGISTERED_KEY =
    "revenuecat.compileSwiftIosArtifactsRegistered"

internal fun Project.shouldSkipSwiftBuild(): Boolean =
    findProperty("skipSwiftBuild")?.toString()?.toBooleanStrictOrNull() == true

internal fun Project.ensureAggregateCompileSwiftIosArtifactsTaskRegistered() {
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
                aggregate.dependsOn(
                    subproject.tasks.matching { task ->
                        task.name.startsWith("compileSwift") &&
                            iosSwiftTaskSuffixes.any { suffix -> task.name.endsWith(suffix) }
                    },
                )
            }
        }
    }
}
