plugins {
    id("revenuecat-library")
}

revenueCat {
    dokka = true
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
    namespace = "com.revenuecat.purchases.kmp.datetime"
}
