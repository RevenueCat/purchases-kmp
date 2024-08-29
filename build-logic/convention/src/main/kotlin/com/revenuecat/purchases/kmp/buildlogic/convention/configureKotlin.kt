package com.revenuecat.purchases.kmp.buildlogic.convention

import com.revenuecat.purchases.kmp.buildlogic.ktx.getVersion
import com.revenuecat.purchases.kmp.buildlogic.ktx.versionCatalog
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlin() {
    extensions.configure<KotlinMultiplatformExtension> {
        // Compilation targets:
        androidTarget {
            compilations.all {
                kotlinOptions {
                    jvmTarget = versionCatalog.getVersion("java")
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
                compilerOptions.configure {
                    freeCompilerArgs.apply {
                        add("-Xexpect-actual-classes")
                    }
                }
            }
        }
        sourceSets.all {
            languageSettings.apply {
                if (name.lowercase().startsWith("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                }
            }
        }

        // Explicit API:
        explicitApi()
    }
}
