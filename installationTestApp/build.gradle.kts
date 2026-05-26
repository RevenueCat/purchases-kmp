plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

// Minimal consumer app for release CI. Uses the minimum supported Kotlin version from the root
// catalog and Maven Local SDK artifacts (see validate-ios-consumer-link in .circleci/config.yml).
kotlin {
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

            val version = libs.versions.revenuecat.kmp.get()
            implementation("com.revenuecat.purchases:purchases-kmp-core:$version")
            implementation("com.revenuecat.purchases:purchases-kmp-result:$version")
            implementation("com.revenuecat.purchases:purchases-kmp-either:$version")
            implementation("com.revenuecat.purchases:purchases-kmp-ui:$version")
        }
    }
}
