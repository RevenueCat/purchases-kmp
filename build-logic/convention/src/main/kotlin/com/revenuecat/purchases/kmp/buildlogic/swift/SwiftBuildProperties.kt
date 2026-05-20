package com.revenuecat.purchases.kmp.buildlogic.swift

import org.gradle.api.Project
import java.util.concurrent.atomic.AtomicBoolean

private val precompiledSwiftAggregateTaskRegistered = AtomicBoolean(false)

internal fun Project.shouldSkipSwiftBuild(): Boolean =
    findProperty("skipSwiftBuild")?.toString()?.toBooleanStrictOrNull() == true

internal fun Project.getSwiftDeveloperDir(): String? =
    findProperty("swiftDeveloperDir")?.toString()?.takeIf { it.isNotBlank() }
        ?: providers.environmentVariable("SWIFT_DEVELOPER_DIR").orNull?.takeIf { it.isNotBlank() }

internal fun Project.ensurePrecompiledSwiftIosArtifactsAggregateTask() {
    if (!precompiledSwiftAggregateTaskRegistered.compareAndSet(false, true)) return

    gradle.projectsEvaluated {
        rootProject.tasks.register("compilePrecompiledSwiftIosArtifacts") {
            group = "build"
            description =
                "Compiles Swift static libraries for all iOS Kotlin/Native targets in kn-core and kn-ui."
            val iosSwiftTaskSuffixes = listOf("IosArm64", "IosSimulatorArm64", "IosX64")
            rootProject.subprojects.forEach { subproject ->
                subproject.tasks.matching { task ->
                    task.name.startsWith("compileSwift") &&
                        iosSwiftTaskSuffixes.any { suffix -> task.name.endsWith(suffix) }
                }.configureEach { dependsOn(this) }
            }
        }
    }
}
