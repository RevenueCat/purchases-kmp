@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        maven("https://packages.jetbrains.team/maven/p/kt/dev")
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
