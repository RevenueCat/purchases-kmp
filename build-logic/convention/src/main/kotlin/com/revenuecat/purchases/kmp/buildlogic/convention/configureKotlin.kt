package com.revenuecat.purchases.kmp.buildlogic.convention

import com.revenuecat.purchases.kmp.buildlogic.ktx.getVersion
import com.revenuecat.purchases.kmp.buildlogic.ktx.versionCatalog
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.TestExecutable

internal fun Project.configureKotlin() {
    extensions.configure<KotlinMultiplatformExtension> {
        // Compilation targets:
        androidTarget {
            compilerOptions {
                compilations.all {
                    jvmTarget.set(JvmTarget.fromTarget(versionCatalog.getVersion("java")))
                }
            }

            publishLibraryVariants("release")
        }
        iosX64()
        iosArm64()
        iosSimulatorArm64()

        // Compiler flags:
        targets.all {
            compilations.all {
                compileTaskProvider.get().compilerOptions {
                    freeCompilerArgs.apply {
                        add("-Xexpect-actual-classes")
                    }
                }
            }
            // Kotlin/Native's linker adds no Swift runtime rpath, so `libswift_Concurrency`
            // would not resolve. Xcode adds it when linking consumer apps.
            if (this is KotlinNativeTarget) {
                binaries.withType<TestExecutable>().configureEach {
                    linkerOpts("-rpath", "/usr/lib/swift")
                }
            }
        }
        sourceSets.all {
            languageSettings.apply {
                val appleSourceSetPrefixes = listOf("apple", "ios", "watchos", "tvos", "macos")
                if (appleSourceSetPrefixes.any { name.lowercase().startsWith(it) }) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                    // NSInteger and friends commonize to types of different widths on different
                    // Apple targets (e.g. 64 bits on iOS, 32 bits on watchosArm64), which requires
                    // this opt-in in shared Apple code.
                    optIn("kotlinx.cinterop.UnsafeNumber")
                }
            }
        }

        // Explicit API:
        explicitApi()
    }
}
