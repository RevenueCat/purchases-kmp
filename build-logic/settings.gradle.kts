@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("../build-settings")
}

plugins {
    id("revenuecat-repositories")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
