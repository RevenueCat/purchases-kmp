plugins {
    id("revenuecat-library")
}

revenueCat {
    dokka = true
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.revenuecat.common)
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
