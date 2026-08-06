package com.revenuecat.purchases.kmp.buildlogic

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Declares all watchOS compilation targets. The `revenuecat-library` convention plugin declares
 * Android and iOS targets for every module; modules that also support watchOS call this from
 * their `kotlin {}` block.
 */
fun KotlinMultiplatformExtension.watchosTargets() {
    watchosArm64()
    watchosDeviceArm64()
    watchosSimulatorArm64()
}
