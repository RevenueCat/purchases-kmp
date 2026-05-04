rootProject.name = "e2etests"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("rootLibs") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
    repositories {
        mavenLocal {
            mavenContent {
                includeModuleByRegex("com\\.revenuecat\\.purchases", "purchases-kmp-.*")
            }
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral {
            mavenContent {
                excludeModuleByRegex("com\\.revenuecat\\.purchases", "purchases-kmp-.*")
            }
        }
    }
}

include(":composeApp")