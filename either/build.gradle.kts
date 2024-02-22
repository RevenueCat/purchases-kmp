plugins {
    id("kobankat-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
        }
    }
}

android {
    namespace = "io.shortway.kobankat.either"
}
