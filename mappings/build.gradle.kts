import com.revenuecat.purchases.kmp.buildlogic.swift.swiftPackage
import com.revenuecat.purchases.kmp.buildlogic.watchosTargets

plugins {
    id("revenuecat-library")
}

kotlin {
    watchosTargets()

    sourceSets {
        commonMain.dependencies {
            api(projects.models)
        }
        androidMain.dependencies {
            api(libs.revenuecat.android)
        }
        appleMain.dependencies {
            implementation(projects.knCore)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test.annotations)
            implementation(libs.kotlin.test.assertions)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
        appleTest.dependencies {
            swiftPackage(
                path = file("src/appleTest/swift"),
                target = "MappingsTestSwift",
                packageName = "com.revenuecat.purchases.kmp.mappings.testswift"
            )
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.mappings"
}
