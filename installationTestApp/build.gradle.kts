plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

val usePublishedMavenLocalArtifacts = providers.gradleProperty("usePublishedMavenLocalArtifacts")
    .map { it.equals("true", ignoreCase = true) }
    .orElse(false)

// Minimal consumer app for release CI. Uses the minimum supported Kotlin version from the root
// catalog. Resolves SDK from project dependencies locally; Maven Local in consumer CI jobs.
kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                if (name.lowercase().startsWith("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                }
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)

            if (usePublishedMavenLocalArtifacts.get()) {
                val version = libs.versions.revenuecat.kmp.get()
                implementation("com.revenuecat.purchases:purchases-kmp-core:$version")
                implementation("com.revenuecat.purchases:purchases-kmp-result:$version")
                implementation("com.revenuecat.purchases:purchases-kmp-either:$version")
                implementation("com.revenuecat.purchases:purchases-kmp-ui:$version")
            } else {
                implementation(project(":core"))
                implementation(project(":result"))
                implementation(project(":either"))
                implementation(project(":revenuecatui"))
            }
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.installationtest"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.revenuecat.purchases.kmp.installationtest"
        minSdk = 24 // revenuecatui requires minSdk 24
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility(libs.versions.java.get())
        targetCompatibility(libs.versions.java.get())
    }
}
