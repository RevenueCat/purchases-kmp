plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    // Compilation targets:
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.java.get()
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

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(projects.core)
            implementation(projects.datetime)
            implementation(projects.either)
            implementation(projects.revenuecatui)
            implementation(projects.result)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.apitester"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility(libs.versions.java.get())
        targetCompatibility(libs.versions.java.get())
    }
}
