import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("revenuecat-library")
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.codingfeline.buildkonfig)
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            api(libs.revenuecat.common)
            implementation(libs.androidx.startup)
        }
    }

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "11.0"

        framework {
            baseName = "Purchases"
            isStatic = true
        }

        pod("PurchasesHybridCommon") {
            version = libs.versions.revenuecat.common.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp"
}

buildkonfig {
    packageName = "com.revenuecat.purchases.kmp"

    defaultConfigs {
        buildConfigField(STRING, "platformFlavor", "kmp")
        buildConfigField(STRING, "revenuecatCommonVersion", libs.versions.revenuecat.common.get())
        buildConfigField(STRING, "revenuecatKmpVersion", libs.versions.revenuecat.kmp.get())
    }
}
