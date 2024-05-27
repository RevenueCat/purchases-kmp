@file:Suppress("UnusedReceiverParameter", "ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT")

package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCDangerousSettings
import cocoapods.PurchasesHybridCommon.RCPurchases
import cocoapods.PurchasesHybridCommon.configureWithAPIKey
import io.shortway.kobankat.models.BillingFeature
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

    public actual val frameworkVersion: String
        get() = Purchases.frameworkVersion()

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
    ): Purchases =
        configuration.run {
            RCPurchases.configureWithAPIKey(
                apiKey = apiKey,
                appUserID = appUserId,
                observerMode = observerMode,
                userDefaultsSuiteName = null,
                platformFlavor = "kmp", // FIXME revisit
                platformFlavorVersion = "0.0.1", // FIXME revisit
                usesStoreKit2IfAvailable = true,
                dangerousSettings = configuration.dangerousSettings.toRCDangerousSettings(),
                shouldShowInAppMessagesAutomatically = showInAppMessagesAutomatically,
            )
        }

    public actual fun canMakePayments(
        features: List<BillingFeature>,
        callback: (Boolean) -> Unit
    ) {
        callback(Purchases.canMakePayments())
    }
}

private fun DangerousSettings.toRCDangerousSettings(): RCDangerousSettings =
    RCDangerousSettings(autoSyncPurchases)
