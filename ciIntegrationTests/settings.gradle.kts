@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        // mavenLocal() must come first so CI can resolve purchases-kmp artifacts
        // published by build-libraries-ios before this job runs.
        mavenLocal()
        google()
        mavenCentral()
    }
}

rootProject.name = "ci-integration-tests"
