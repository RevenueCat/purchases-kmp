// Standalone integration test project.
//
// Purpose: verify that a consumer app can compile against purchases-kmp-core published
// to mavenLocal by build-libraries-ios (Xcode 16) when running on a newer Xcode version.
// This exercises the scenario reported in issue #759.
//
// Note: we intentionally skip the final link step here. The Kotlin CocoaPods plugin
// triggers an Android cmdline-tools version check (throwing on the 25.0.2 version
// pre-installed on the Xcode 26.x CI machine), so we use plain KMP without CocoaPods
// and verify compilation only. The compile step still resolves and processes the
// Xcode-16-built klib on the Xcode 26 toolchain, which is where the incompatibility
// from #759 would also surface.
//
// Run with: ../gradlew compileKotlinIosSimulatorArm64 -PpurchasesKmpVersion=<version>

plugins {
    kotlin("multiplatform") version "2.1.21"
}

val purchasesKmpVersion: String by project

kotlin {
    iosSimulatorArm64()

    sourceSets {
        iosMain.dependencies {
            implementation("com.revenuecat.purchases:purchases-kmp-core:$purchasesKmpVersion")
        }
    }
}
