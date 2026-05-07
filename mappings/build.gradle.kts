plugins {
    id("revenuecat-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.models)
        }
        androidMain.dependencies {
            api(libs.revenuecat.android)
        }
        iosMain.dependencies {
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
