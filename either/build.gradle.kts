plugins {
    id("kobankat-library")
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
    namespace = "io.shortway.kobankat.either"
}
