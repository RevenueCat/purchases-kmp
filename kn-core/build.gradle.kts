import com.revenuecat.purchases.kmp.buildlogic.swift.swiftPackage

plugins {
    id("revenuecat-library")
}

kotlin {
    sourceSets {
        iosMain.dependencies {
            swiftPackage(
                path = rootProject.file("upstream/purchases-ios"),
                target = "RevenueCat",
                packageName = "swiftPMImport.com.revenuecat.purchases.kn.core"
            )
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kn.core"
}
