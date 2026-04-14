import com.revenuecat.purchases.kmp.buildlogic.swift.swiftPackage

plugins {
    id("revenuecat-library")
}

kotlin {
    sourceSets {
        iosMain.dependencies {
            swiftPackage(
                path = rootProject.file("upstream/purchases-ios"),
                target = "RevenueCat",
                packageName = "swiftPMImport.com.revenuecat.purchases.kn.core",
                customDeclarations = """
                    // Force cinterop binding generation for types otherwise not in the public API
                    static inline int __forceBindings(
                        enum RCStoreMessageType _1
                    ) { return 0; }
                """.trimIndent()
            )

            swiftPackage(
                path = file("src/swift"),
                target = "AdditionalSwift",
                packageName = "swiftPMImport.com.revenuecat.purchases.kn.core.additional"
            )
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kn.core"
}
