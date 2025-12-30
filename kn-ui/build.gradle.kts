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
            //api(projects.knCore)
            swiftPackage(
                path = rootProject.file("upstream/purchases-ios"),
                target = "RevenueCatUI",
                packageName = "swiftPMImport.com.revenuecat.purchases.kn.ui"
            )
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kn.ui"
}
