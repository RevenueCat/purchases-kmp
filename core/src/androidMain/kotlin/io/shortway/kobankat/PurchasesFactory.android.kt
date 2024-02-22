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

    private var application: Application? = null

    /**
     * Call this once, in [Application.onCreate], before calling any other methods.
     */
    public fun setApplication(application: Application) {
        check(this.application == null) {
            "setApplication() should only be called once, in Application.onCreate()."
        }
        this.application = application
        requireApplication().registerActivityLifecycleCallbacks(ActivityProvider)
    }

    public actual fun configure(
        configuration: PurchasesConfiguration
    ): Purchases =
        Purchases.configure(configuration.toRcPurchasesConfiguration(requireApplication()))

    @JvmOverloads
    @JvmStatic
    public actual fun canMakePayments(
        features: List<BillingFeature>,
        callback: (Boolean) -> Unit,
    ): Unit = Purchases.canMakePayments(
        context = requireApplication(),
        features = features
    ) { result -> callback(result) }

    private fun requireApplication(): Application =
        application
            ?: error(
                "Be sure to call setApplication() in Application.onCreate, " +
                        "before calling any other methods."
            )
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
    when (this) {
        EntitlementVerificationMode.DISABLED -> RcEntitlementVerificationMode.DISABLED
        EntitlementVerificationMode.INFORMATIONAL -> RcEntitlementVerificationMode.INFORMATIONAL
    }