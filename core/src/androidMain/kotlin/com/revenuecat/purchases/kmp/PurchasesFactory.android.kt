@file:Suppress("UnusedReceiverParameter")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.common.PlatformInfo
import com.revenuecat.purchases.kmp.di.AndroidProvider
import com.revenuecat.purchases.kmp.di.requireApplication
import com.revenuecat.purchases.kmp.models.BillingFeature
import io.shortway.kobankat.BuildKonfig
import java.net.URL
import com.revenuecat.purchases.DangerousSettings as RcDangerousSettings
import com.revenuecat.purchases.Purchases as RcPurchases
import com.revenuecat.purchases.hybridcommon.configure as commonConfigure

public actual object PurchasesFactory {
    public actual val sharedInstance: Purchases
        get() = Purchases.sharedInstance

    @JvmStatic
    public actual var logLevel: LogLevel by RcPurchases.Companion::logLevel

    @JvmStatic
    public actual var logHandler: LogHandler by RcPurchases.Companion::logHandler

    @JvmStatic
    public actual var proxyURL: String?
        get() = Purchases.proxyURL?.toString()
        set(value) { Purchases.proxyURL = value?.let { URL(it) } }

    @JvmStatic
    public actual val isConfigured: Boolean by RcPurchases.Companion::isConfigured

    @JvmStatic
    public actual var simulatesAskToBuyInSandbox: Boolean = false

    @JvmStatic
    public actual var forceUniversalAppStore: Boolean = false

    public actual fun configure(
        configuration: PurchasesConfiguration
    ): Purchases {
        with(configuration) {
            // Using the common configure() call allows us to pass PlatformInfo.
            commonConfigure(
                AndroidProvider.requireApplication(),
                apiKey = apiKey,
                appUserID = appUserId,
                observerMode = observerMode,
                platformInfo = PlatformInfo(
                    flavor = BuildKonfig.platformFlavor,
                    version = frameworkVersion,
                ),
                store = store ?: Store.PLAY_STORE,
                dangerousSettings = dangerousSettings.toRcDangerousSettings(),
                shouldShowInAppMessagesAutomatically = showInAppMessagesAutomatically,
                verificationMode = verificationMode.name,
            )
        }

        return sharedInstance
    }

    @JvmOverloads
    @JvmStatic
    public actual fun canMakePayments(
        features: List<BillingFeature>,
        callback: (Boolean) -> Unit,
    ): Unit = Purchases.canMakePayments(
        context = AndroidProvider.requireApplication(),
        features = features
    ) { result -> callback(result) }
}

private fun DangerousSettings.toRcDangerousSettings(): RcDangerousSettings =
    RcDangerousSettings(autoSyncPurchases)
