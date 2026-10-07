plugins {
    id("revenuecat-library")
}

revenueCat {
    dokka = true
}

kotlin {
    // Not using watchosTargets() because arrow-core does not publish watchosDeviceArm64.
    watchosArm64()
    watchosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.arrow.core)
            implementation(projects.core)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.either"
}
