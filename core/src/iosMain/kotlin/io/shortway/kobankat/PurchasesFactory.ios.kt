@file:Suppress("UnusedReceiverParameter", "ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT")

package io.shortway.kobankat

import cocoapods.RevenueCat.RCConfiguration
import cocoapods.RevenueCat.RCDangerousSettings
import cocoapods.RevenueCat.RCEntitlementVerificationMode
import cocoapods.RevenueCat.RCEntitlementVerificationModeDisabled
import cocoapods.RevenueCat.RCEntitlementVerificationModeInformational
import cocoapods.RevenueCat.RCPurchases
import cocoapods.RevenueCat.configureWithConfiguration
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
    
    public actual fun canMakePayments(
        features: List<BillingFeature>,
        callback: (Boolean) -> Unit
    ) {
        callback(Purchases.canMakePayments())
    }
}

public fun PurchasesFactory.configure(
    configuration: PurchasesConfiguration
): Purchases =
    RCPurchases.configureWithConfiguration(configuration.toRCConfiguration())

private fun PurchasesConfiguration.toRCConfiguration(): RCConfiguration =
    RCConfiguration.builderWithAPIKey(apiKey = apiKey)
        .withAppUserID(appUserId)
        .withObserverMode(observerMode)
        .withShowStoreMessagesAutomatically(showInAppMessagesAutomatically)
        .withDangerousSettings(dangerousSettings.toRCDangerousSettings())
        .withEntitlementVerificationMode(verificationMode.toRCEntitlementVerificationMode())
        .build()

private fun DangerousSettings.toRCDangerousSettings(): RCDangerousSettings =
    RCDangerousSettings(autoSyncPurchases)

private fun EntitlementVerificationMode.toRCEntitlementVerificationMode(): RCEntitlementVerificationMode =
    when(this) {
        EntitlementVerificationMode.DISABLED -> RCEntitlementVerificationModeDisabled
        EntitlementVerificationMode.INFORMATIONAL -> RCEntitlementVerificationModeInformational
    }