@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            // Allows overriding the kotlin version using the `kotlinVersion` property.
            // https://docs.gradle.org/current/userguide/build_environment.html#sec:project_properties
            if (requested.id.namespace?.startsWith("org.jetbrains.kotlin.") == true) {
                val kotlinVersion = providers.gradleProperty("kotlinVersion")
                if (kotlinVersion.isPresent) {
                    useVersion(kotlinVersion.get())
                }
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
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
