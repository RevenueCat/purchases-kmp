import com.revenuecat.purchases.kmp.buildlogic.swift.swiftPackage

plugins {
    id("revenuecat-library")
}

kotlin {
    sourceSets {
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
