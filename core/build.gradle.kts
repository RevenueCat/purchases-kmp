plugins {
    id("kobankat.library")
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            api(libs.revenuecat.android)
        }
    }

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "11.0"

        framework {
            baseName = "KobanKat"
            isStatic = true
        }

        pod("RevenueCat") {
            version = libs.versions.revenuecat.ios.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}

android {
    namespace = "io.shortway.kobankat"
}
