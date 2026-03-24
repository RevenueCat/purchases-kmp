// Standalone integration test project.
//
// Purpose: verify that an app consuming purchases-kmp-core (published to mavenLocal
// by build-libraries-ios using Xcode 16) can be linked by a newer Xcode version.
// This reproduces the scenario reported in issue #759, where end-users build their
// app with Xcode 26.x and hit a linker error against the pre-built KMP binary.
//
// Run with: ../gradlew linkDebugFrameworkIosSimulatorArm64 -PpurchasesKmpVersion=<version>

plugins {
    kotlin("multiplatform") version "2.1.21"
    kotlin("native.cocoapods") version "2.1.21"
}

val purchasesKmpVersion: String by project

kotlin {
    iosSimulatorArm64()

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "13.0"

        framework {
            baseName = "CiIntegrationTest"
            isStatic = true
        }

        // PurchasesHybridCommon is a transitive native dependency of purchases-kmp-core.
        // It is not in Maven, so the consumer must install it via CocoaPods separately.
        pod("PurchasesHybridCommon") {
            version = "17.52.0"
        }
    }

    sourceSets {
        iosMain.dependencies {
            implementation("com.revenuecat.purchases:purchases-kmp-core:$purchasesKmpVersion")
        }
    }
}
