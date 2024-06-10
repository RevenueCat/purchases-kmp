plugins {
    id("revenuecat-library")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.revenuecat.commonUi)
        }
    }

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "11.0"

        pod("PurchasesHybridCommonUI") {
            version = libs.versions.revenuecat.common.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}

android {
    namespace = "com.purchases.revenuecat.kmp.ui.revenuecatui"

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
    dependencies {
        debugImplementation(libs.androidx.compose.ui.tooling)
    }
}
