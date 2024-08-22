plugins {
    id("revenuecat-internal-library")
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.revenuecat.common)
        }
    }

    cocoapods {
        version = libs.versions.revenuecat.kmp.get()
        ios.deploymentTarget = libs.versions.ios.deploymentTarget.get()

        pod("PurchasesHybridCommon") {
            version = libs.versions.revenuecat.common.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.mappings"
}
