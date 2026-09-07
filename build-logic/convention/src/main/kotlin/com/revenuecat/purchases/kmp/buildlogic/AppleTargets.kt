package com.revenuecat.purchases.kmp.buildlogic

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

// The `revenuecat-library` convention plugin declares Android and iOS targets for every module.
// Modules that also support one of the Apple families below opt in from their `kotlin {}` block.

/** Declares all watchOS compilation targets. */
fun KotlinMultiplatformExtension.watchosTargets() {
    watchosArm64()
    watchosDeviceArm64()
    watchosSimulatorArm64()
}

/** Declares all macOS compilation targets. */
fun KotlinMultiplatformExtension.macosTargets() {
    macosArm64()
    macosX64()
}
