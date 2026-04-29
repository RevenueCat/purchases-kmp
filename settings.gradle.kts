@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-settings")
    includeBuild("build-logic")
}

plugins {
    id("revenuecat-repositories")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
