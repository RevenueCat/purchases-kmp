plugins {
    id("revenuecat-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
        }
    }
}

android {
    namespace = "com.purchases.revenuecat.kmp.result"
}
