plugins {
    id("revenuecat-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
            implementation(projects.core)
        }
    }
}

android {
    namespace = "com.purchases.revenuecat.kmp.datetime"
}
