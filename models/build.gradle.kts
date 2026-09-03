import com.revenuecat.purchases.kmp.buildlogic.macosTargets
import com.revenuecat.purchases.kmp.buildlogic.watchosTargets

plugins {
    id("revenuecat-library")
    alias(libs.plugins.poko)
}

revenueCat {
    dokka = true
}

kotlin {
    watchosTargets()
    macosTargets()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.revenuecat.android)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test.annotations)
            implementation(libs.kotlin.test.assertions)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.models"
}
