@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // TODO: remove with the purchases-android 11.0.0-SNAPSHOT pin, before merging.
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            content { includeGroup("com.revenuecat.purchases") }
        }
    }
}

rootProject.name = "purchases-kmp"
include(":apiTester")
include(":composeApp")
include(":core")
include(":either")
include(":kn-core")
include(":kn-ui")
include(":mappings")
include(":models")
include(":result")
include(":watchosTester")
include(":revenuecatui")
include(":e2e-tests:MaestroTestApp")
