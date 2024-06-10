plugins {
    id("revenuecat-library")
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
    namespace = "com.purchases.revenuecat.kmp.either"
}
