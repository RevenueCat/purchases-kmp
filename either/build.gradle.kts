plugins {
    id("revenuecat-public-library")
}

kotlin {
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
