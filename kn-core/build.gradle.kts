import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftSettings
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
                """.trimIndent(),
                // Bypass purchases-ios' Test Store Release-build crash. purchases-kmp distributes
                // purchases-ios as a pre-compiled binary built in Release configuration, so the
                // safeguard would otherwise crash apps using a Test Store API key during
                // development. See https://github.com/RevenueCat/purchases-kmp/issues/823.
                swiftSettings = SwiftSettings {
                    define("BYPASS_SIMULATED_STORE_RELEASE_CHECK")
                }
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
