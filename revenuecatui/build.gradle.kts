plugins {
    id("revenuecat-public-library")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            implementation(projects.mappings)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
        }
        androidMain.dependencies {
            implementation(libs.revenuecat.commonUi)
        }
    }

    cocoapods {
        version = libs.versions.revenuecat.kmp.get()
        ios.deploymentTarget = libs.versions.ios.deploymentTarget.get()

        pod("PurchasesHybridCommonUI") {
            version = libs.versions.revenuecat.common.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.ui.revenuecatui"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = 24
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
