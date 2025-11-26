@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven("https://packages.jetbrains.team/maven/p/kt/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://packages.jetbrains.team/maven/p/kt/dev")
        google()
        mavenCentral()
    }
}

rootProject.name = "purchases-kmp"
include(":apiTester")
include(":composeApp")
include(":core")
include(":datetime")
include(":either")
include(":mappings")
include(":models")
include(":result")
include(":revenuecatui")
