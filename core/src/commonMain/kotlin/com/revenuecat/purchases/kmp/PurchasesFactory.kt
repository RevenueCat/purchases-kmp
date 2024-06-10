package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.PurchasesFactory.configure
import com.revenuecat.purchases.kmp.PurchasesFactory.sharedInstance
import com.revenuecat.purchases.kmp.models.BillingFeature
import io.shortway.kobankat.BuildKonfig
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Factory of [Purchases]. Call [configure] once before accessing the singleton Purchases instance
 * using [sharedInstance].
 */
public expect object PurchasesFactory {

    /**
     * Singleton instance of Purchases. [configure] will set this. Will throw an exception if the
     * shared instance has not been configured.
     *
     * @return A previously set singleton Purchases instance.
     */
    public val sharedInstance: Purchases

    /**
     * Configure log level. Useful for debugging issues with the lovely team @RevenueCat
     * By default, LogLevel.DEBUG in debug builds, and LogLevel.INFO in release builds.
     */
    @JvmStatic
    public var logLevel: LogLevel

    /**
     * Set a custom log handler for redirecting logs to your own logging system.
     *
     * By default, this sends info, warning, and error messages.
     * If you wish to receive Debug level messages, see [logLevel].
     */
    @JvmStatic
    public var logHandler: LogHandler

    /**
     * Set this property to your proxy URL before configuring Purchases *only*
     * if you've received a proxy key value from your RevenueCat contact.
     */
    @JvmStatic
    public var proxyURL: String?

    /**
     * True if `configure` has been called and [sharedInstance] is set
     */
    @JvmStatic
    public val isConfigured: Boolean

    /**
     * App Store only. Set this property to true only when testing the ask-to-buy / SCA purchases
     * flow. More information [available here](https://rev.cat/ask-to-buy).
     */
    @JvmStatic
    public var simulatesAskToBuyInSandbox: Boolean

    /**
     * App Store only. Set this property to true only if you’re transitioning an existing Mac app
     * from the Legacy Mac App Store into the Universal Store, and you’ve configured your
     * RevenueCat app accordingly. Contact RevenueCat support before using this.
     */
    @JvmStatic
    public var forceUniversalAppStore: Boolean

    /**
     * Configures an instance of the SDK with the specified [configuration]. The instance will be
     * set as a singleton. You should access the singleton instance using [sharedInstance].
     */
    public fun configure(configuration: PurchasesConfiguration): Purchases

    /**
     * Note: This method only works for the Google Play Store and App Store. There is no Amazon
     * equivalent at this time. Calling from an Amazon-configured app will return true.
     *
     * Check if billing is supported for the current Play user (meaning IN-APP purchases are
     * supported) and optionally, whether all features in the list of specified feature types are
     * supported. This method is asynchronous since it requires a connected BillingClient.
     *
     * @param features Play Store only. A list of feature types to check for support. Feature types
     * must be one of [BillingFeature]. By default, is an empty list and no specific feature
     * support will be checked.
     * @param callback Callback that will be notified when the check is complete.
     */
    @JvmStatic
    @JvmOverloads
    public fun canMakePayments(
        features: List<BillingFeature> = listOf(),
        callback: (Boolean) -> Unit,
    )

}

/**
 * Current version of the Purchases SDK
 */
@Suppress("UnusedReceiverParameter")
public val PurchasesFactory.frameworkVersion: String
    get() = BuildKonfig.revenuecatKmpVersion
