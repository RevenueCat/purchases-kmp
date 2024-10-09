import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    id("revenuecat-public-library")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.cocoapods)
}

println("TEST kotlinPluginVersion: ${getKotlinPluginVersion()}")
// We should be comparing for 2.0.20 to be precise, but this is good enough for now.
val kotlinVersion = providers.gradleProperty("kotlinVersion").orNull
    ?: libs.versions.kotlin.get()
println("TEST kotlinVersion: ${kotlinVersion}")

if (kotlinVersion.split('.').first().toInt() >= 2) {
    error("TEST")
    apply(plugin = "org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.models)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
        }
        androidMain.dependencies {
            implementation(libs.revenuecat.commonUi)
            implementation(projects.mappings)
        }
        iosMain.dependencies {
            implementation(projects.mappings)
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
