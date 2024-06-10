@file:Suppress("UnusedReceiverParameter", "ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT")

package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCDangerousSettings
import cocoapods.PurchasesHybridCommon.RCPurchases
import cocoapods.PurchasesHybridCommon.configureWithAPIKey
import com.revenuecat.purchases.kmp.models.BillingFeature
import io.shortway.kobankat.BuildKonfig
import platform.Foundation.NSURL

public actual object PurchasesFactory {
    public actual val sharedInstance: Purchases
        get() = Purchases.sharedPurchases()

    public actual var logLevel: LogLevel
        get() = Purchases.logLevel().toLogLevel()
        set(value) = Purchases.setLogLevel(value.toRcLogLevel())

    public actual var logHandler: LogHandler
        get() = Purchases.logHandler().toLogHandler()
        set(value) = Purchases.setLogHandler(value.toRcLogHandler())

    public actual var proxyURL: String?
        get() = Purchases.proxyURL()?.absoluteString()
        set(value) = Purchases.setProxyURL(value?.let { NSURL(string = it) })

    public actual val isConfigured: Boolean
        get() = Purchases.isConfigured()

    public actual var simulatesAskToBuyInSandbox: Boolean
        get() = Purchases.simulatesAskToBuyInSandbox()
        set(value) = Purchases.setSimulatesAskToBuyInSandbox(value)

    public actual var forceUniversalAppStore: Boolean
        get() = Purchases.forceUniversalAppStore()
        set(value) = Purchases.setForceUniversalAppStore(value)

    public actual fun configure(
        configuration: PurchasesConfiguration
    ): Purchases {
        // TODO Enable this in SDK-3301 checkCommonVersion()

        return with(configuration) {
            RCPurchases.configureWithAPIKey(
                apiKey = apiKey,
                appUserID = appUserId,
                observerMode = observerMode,
                userDefaultsSuiteName = userDefaultsSuiteName,
                platformFlavor = BuildKonfig.platformFlavor,
                platformFlavorVersion = frameworkVersion,
                usesStoreKit2IfAvailable = false, // In Flutter it's deprecated & defaults to false.
                dangerousSettings = dangerousSettings.toRCDangerousSettings(),
                shouldShowInAppMessagesAutomatically = showInAppMessagesAutomatically,
                verificationMode = verificationMode.name,
            )
        }
    }

    public actual fun canMakePayments(
        features: List<BillingFeature>,
        callback: (Boolean) -> Unit
    ) {
        callback(Purchases.canMakePayments())
    }

    private fun checkCommonVersion() {
        val expected = BuildKonfig.revenuecatCommonVersion
        val actual = Purchases.frameworkVersion()
        check(actual == expected) {
            "Unexpected version of PurchasesHybridCommon ('$actual'). Make sure to use " +
                    "'$expected' exactly."
        }
    }
}

private fun DangerousSettings.toRCDangerousSettings(): RCDangerousSettings =
    RCDangerousSettings(autoSyncPurchases)
