plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    watchosSimulatorArm64 {
        binaries.framework {
            baseName = "WatchTester"
            isStatic = true
            export(projects.core)
            export(projects.models)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.models)
        }
    }
}
