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
        if (usePublishedMavenLocalArtifacts()) {
            mavenLocal()
        }
        google()
        mavenCentral()
    }
}

fun usePublishedMavenLocalArtifacts(): Boolean {
    val gradleProperties = java.util.Properties()
    val gradlePropertiesFile = File(settingsDir, "gradle.properties")
    if (gradlePropertiesFile.exists()) {
        gradlePropertiesFile.inputStream().use { gradleProperties.load(it) }
    }
    return gradleProperties.getProperty("usePublishedMavenLocalArtifacts") == "true" ||
        startParameter.projectProperties["usePublishedMavenLocalArtifacts"] == "true" ||
        System.getenv("ORG_GRADLE_PROJECT_usePublishedMavenLocalArtifacts") == "true"
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
if (usePublishedMavenLocalArtifacts()) {
    include(":installationTestApp")
}
