plugins {
    id("revenuecat-public-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.datetime"
}
