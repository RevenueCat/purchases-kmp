package io.shortway.kobankat

import io.shortway.kobankat.models.BillingFeature
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

public expect object PurchasesFactory {
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
     * Current version of the Purchases SDK
     */
    @JvmStatic
    public val frameworkVersion: String

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
