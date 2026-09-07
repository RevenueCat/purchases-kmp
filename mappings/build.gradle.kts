import com.revenuecat.purchases.kmp.buildlogic.macosTargets
import com.revenuecat.purchases.kmp.buildlogic.watchosTargets

plugins {
    id("revenuecat-library")
}

kotlin {
    watchosTargets()
    macosTargets()

    sourceSets {
        commonMain.dependencies {
            api(projects.models)
        }
        androidMain.dependencies {
            api(libs.revenuecat.android)
        }
        appleMain.dependencies {
            implementation(projects.knCore)
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
    namespace = "com.revenuecat.purchases.kmp.mappings"
}
