import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftSettings
import com.revenuecat.purchases.kmp.buildlogic.swift.swiftPackage

plugins {
    id("revenuecat-library")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.runtime)
        }

        iosMain.dependencies {
            swiftPackage(
                path = rootProject.file("upstream/purchases-ios"),
                target = "RevenueCatUI",
                packageName = "swiftPMImport.com.revenuecat.purchases.kn.ui",
                swiftSettings = SwiftSettings {
                    define("COMPOSE_RESOURCES")
                }
            )
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kn.ui"
}
