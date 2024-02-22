plugins {
    id("kobankat-library")
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
    namespace = "io.shortway.kobankat.datetime"
}
