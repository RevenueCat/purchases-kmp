plugins {
    id("revenuecat-public-library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.revenuecat.common)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test.annotations)
            implementation(libs.kotlin.test.assertions)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
    }

     swiftPMDependencies {
        `package`(
            url = url("https://github.com/RevenueCat/purchases-hybrid-common.git"),
            version = exact(libs.versions.revenuecat.common.get()),
            products = listOf(product("PurchasesHybridCommon")),
        )
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.models"
}
