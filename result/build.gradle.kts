plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.adamko.dokkatoo.html)
    alias(libs.plugins.arturbosch.detekt)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.gradleup.nmcp)
}

kotlin {
    explicitApi()
    targets.all {
        compilations.all {
            compilerOptions.configure {
                freeCompilerArgs.apply {
                    add("-Xexpect-actual-classes")
                }
            }
        }
    }

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

    sourceSets {
        all {
            languageSettings.apply {
                if (name.lowercase().startsWith("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                }
            }
        }
        commonMain.dependencies {
            implementation(projects.core)
        }
    }


}

android {
    namespace = "io.shortway.kobankat.result"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility(libs.versions.java.get())
        targetCompatibility(libs.versions.java.get())
    }
}
