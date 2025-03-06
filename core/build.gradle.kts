import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("revenuecat-public-library")
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.codingfeline.buildkonfig)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.models)
        }
        androidMain.dependencies {
            implementation(libs.revenuecat.common)
            implementation(libs.androidx.startup)
            implementation(projects.mappings)
        }
        iosMain.dependencies {
            implementation(projects.mappings)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test.annotations)
            implementation(libs.kotlin.test.assertions)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
    }

    cocoapods {
        version = libs.versions.revenuecat.kmp.get()
        ios.deploymentTarget = libs.versions.ios.deploymentTarget.core.get()

        framework {
            baseName = "Purchases"
            isStatic = true
        }

        pod("PurchasesHybridCommon") {
            version = libs.versions.revenuecat.common.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
            source = git("https://github.com/revenuecat/purchases-hybrid-common") {
                branch = "chore/public-initialiser"
            }
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
