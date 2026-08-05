import com.revenuecat.purchases.kmp.buildlogic.watchosTargets

plugins {
    id("revenuecat-library")
}

revenueCat {
    dokka = true
}

kotlin {
    watchosTargets()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.result"
}
