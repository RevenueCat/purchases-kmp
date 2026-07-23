import com.revenuecat.purchases.kmp.buildlogic.watchosTargets

plugins {
    id("revenuecat-library")
}

kotlin {
    watchosTargets()

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
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.mappings"
}
