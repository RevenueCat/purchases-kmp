plugins {
    id("revenuecat-public-library")
}

kotlin {
    swiftPMDependencies {
        `package`(
            url = url("https://github.com/RevenueCat/purchases-hybrid-common.git"),
            version = exact(libs.versions.revenuecat.common.get()),
            products = listOf(product("PurchasesHybridCommon")),
        )
    }
}

android {
    namespace = "com.revenuecat.purchases.kn.core"
}
