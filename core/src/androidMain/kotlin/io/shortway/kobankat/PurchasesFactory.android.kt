@file:Suppress("UnusedReceiverParameter")

package io.shortway.kobankat

import android.app.Application
import android.content.Context
import io.shortway.kobankat.di.ActivityProvider
import io.shortway.kobankat.models.BillingFeature
import java.net.URL
import com.revenuecat.purchases.DangerousSettings as RcDangerousSettings
import com.revenuecat.purchases.EntitlementVerificationMode as RcEntitlementVerificationMode
import com.revenuecat.purchases.Purchases as RcPurchases
import com.revenuecat.purchases.PurchasesConfiguration as RcPurchasesConfiguration

public actual object PurchasesFactory {
    public actual val sharedInstance: Purchases
        get() = Purchases.sharedInstance

    @JvmStatic
    public actual var logLevel: LogLevel by RcPurchases.Companion::logLevel

    @JvmStatic
    public actual var logHandler: LogHandler by RcPurchases.Companion::logHandler

    @JvmStatic
    public actual val frameworkVersion: String by RcPurchases.Companion::frameworkVersion

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

    @JvmOverloads
    @JvmStatic
    public actual fun canMakePayments(
        features: List<BillingFeature>,
        callback: (Boolean) -> Unit,
    ): Unit = Purchases.canMakePayments(
        context = ActivityProvider.current
            ?: error(
                "Make sure you call PurchasesFactory.configure(), " +
                        "preferably in Application.onCreate()."
            ),
        features = features
    ) { result -> callback(result) }
}

public fun PurchasesFactory.configure(
    context: Context,
    configuration: PurchasesConfiguration
): Purchases {
    (context.applicationContext as Application).registerActivityLifecycleCallbacks(ActivityProvider)
    return Purchases.configure(configuration.toRcPurchasesConfiguration(context))
}

private fun PurchasesConfiguration.toRcPurchasesConfiguration(context: Context): RcPurchasesConfiguration =
    RcPurchasesConfiguration.Builder(context = context, apiKey = apiKey)
        .appUserID(appUserId)
        .observerMode(observerMode)
        .showInAppMessagesAutomatically(showInAppMessagesAutomatically)
        .dangerousSettings(dangerousSettings.toRcDangerousSettings())
        .entitlementVerificationMode(verificationMode.toRcEntitlementVerificationMode())
        .build()

private fun DangerousSettings.toRcDangerousSettings(): RcDangerousSettings =
    RcDangerousSettings(autoSyncPurchases)

private fun EntitlementVerificationMode.toRcEntitlementVerificationMode(): RcEntitlementVerificationMode =
    when(this) {
        EntitlementVerificationMode.DISABLED -> RcEntitlementVerificationMode.DISABLED
        EntitlementVerificationMode.INFORMATIONAL -> RcEntitlementVerificationMode.INFORMATIONAL
    }