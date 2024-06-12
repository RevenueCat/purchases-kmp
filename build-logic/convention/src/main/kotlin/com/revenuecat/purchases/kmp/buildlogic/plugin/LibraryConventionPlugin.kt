package com.revenuecat.purchases.kmp.buildlogic.plugin

import com.android.build.gradle.LibraryExtension
import com.revenuecat.purchases.kmp.buildlogic.ktx.getVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * A build convention plugin to be applied to all published library modules, to reduce duplication
 * in build scripts.
 */
class LibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        val javaVersion = libs.getVersion("java")

        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.library")
            apply("dev.adamko.dokkatoo-html")
            apply("io.gitlab.arturbosch.detekt")
            apply("com.vanniktech.maven.publish")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            // Compilation targets:
            androidTarget {
                compilations.all {
                    kotlinOptions {
                        jvmTarget = javaVersion
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

        // Android library setup:
        extensions.configure<LibraryExtension> {
            compileSdk = libs.getVersion("android-compileSdk").toInt()
            defaultConfig {
                minSdk = libs.getVersion("android-minSdk").toInt()
            }
            compileOptions {
                sourceCompatibility(javaVersion)
                targetCompatibility(javaVersion)
            }
        }
    }

}
