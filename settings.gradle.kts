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

fun gradlePropertyEnabled(name: String): Boolean {
    val gradleProperties = java.util.Properties()
    val gradlePropertiesFile = File(settingsDir, "gradle.properties")
    if (gradlePropertiesFile.exists()) {
        gradlePropertiesFile.inputStream().use { gradleProperties.load(it) }
    }
    return gradleProperties.getProperty(name) == "true" ||
        startParameter.projectProperties[name] == "true" ||
        System.getenv("ORG_GRADLE_PROJECT_$name") == "true"
}

fun usePublishedMavenLocalArtifacts(): Boolean =
    gradlePropertyEnabled("usePublishedMavenLocalArtifacts")

fun installationTestAppOnlyBuild(): Boolean =
    gradlePropertyEnabled("installationTestAppOnlyBuild")

rootProject.name = "purchases-kmp"
if (installationTestAppOnlyBuild()) {
    include(":installationTestApp")
} else {
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
    include(":installationTestApp")
}
