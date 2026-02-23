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
                includeGroup("com.revenuecat.purchases")
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
                excludeGroup("com.revenuecat.purchases")
            }
        }
    }
}

include(":composeApp")