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
        val usePublishedMavenLocalArtifacts =
            startParameter.projectProperties["usePublishedMavenLocalArtifacts"] == "true" ||
                System.getenv("ORG_GRADLE_PROJECT_usePublishedMavenLocalArtifacts") == "true"
        if (usePublishedMavenLocalArtifacts) {
            mavenLocal()
        }
        google()
        mavenCentral()
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
include(":revenuecatui")
include(":e2e-tests:MaestroTestApp")
