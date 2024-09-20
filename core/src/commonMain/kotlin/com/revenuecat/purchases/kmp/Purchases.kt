package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.PurchasesConfiguration.Builder
import com.revenuecat.purchases.kmp.models.BillingFeature
import com.revenuecat.purchases.kmp.models.CacheFetchPolicy
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.ReplacementMode
import com.revenuecat.purchases.kmp.models.Store
import com.revenuecat.purchases.kmp.models.StoreMessageType
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import kotlin.jvm.JvmSynthetic

/**
 * Entry point for Purchases. This class can be instantiated using [Purchases.configure]. This
 * should be done as soon as your app has a unique user id for your user. This can be when a user
 * logs in if you have accounts or on launch if you can generate a random user identifier.
 * Make sure you follow the [quickstart](https://docs.revenuecat.com/docs/getting-started-1)
 * guide to setup your RevenueCat account.
 * Only one instance of Purchases should be instantiated at a time! Access the singleton
 * instance with [Purchases.sharedInstance].
 */
public expect class Purchases {
    public companion object {
        /**
         * Singleton instance of Purchases. [configure] will set this. Will throw an exception if
         * the shared instance has not been configured.
         *
         * @return A previously set singleton Purchases instance.
         */
        public val sharedInstance: Purchases

        /**
         * Configure log level. Useful for debugging issues with the lovely team @RevenueCat
         * By default, LogLevel.DEBUG in debug builds, and LogLevel.INFO in release builds.
         */
        public var logLevel: LogLevel

        /**
         * Set a custom log handler for redirecting logs to your own logging system.
         *
         * By default, this sends info, warning, and error messages.
         * If you wish to receive Debug level messages, see [logLevel].
         */
        public var logHandler: LogHandler

        /**
         * Set this property to your proxy URL before configuring Purchases *only*
         * if you've received a proxy key value from your RevenueCat contact.
         */
        public var proxyURL: String?

        /**
         * True if `configure` has been called and [sharedInstance] is set
         */
        public val isConfigured: Boolean

        /**
         * App Store only. Set this property to true only when testing the ask-to-buy / SCA
         * purchases flow. More information [available here](https://rev.cat/ask-to-buy).
         */
        public var simulatesAskToBuyInSandbox: Boolean

        /**
         * App Store only. Set this property to true only if you’re transitioning an existing Mac
         * app from the Legacy Mac App Store into the Universal Store, and you’ve configured your
         * RevenueCat app accordingly. Contact RevenueCat support before using this.
         */
        public var forceUniversalAppStore: Boolean

        /**
         * Configures an instance of the SDK with the specified [configuration]. The instance will
         * be set as a singleton. You should access the singleton instance using [sharedInstance].
         */
        public fun configure(configuration: PurchasesConfiguration): Purchases

        /**
         * Note: This method only works for the Google Play Store and App Store. There is no Amazon
         * equivalent at this time. Calling from an Amazon-configured app will return true.
         *
         * Check if billing is supported for the current Play user (meaning IN-APP purchases are
         * supported) and optionally, whether all features in the list of specified feature types
         * are supported. This method is asynchronous since it requires a connected BillingClient.
         *
         * @param features Play Store only. A list of feature types to check for support. Feature
         * types must be one of [BillingFeature]. By default, is an empty list and no specific
         * feature support will be checked.
         * @param callback Callback that will be notified when the check is complete.
         */
        public fun canMakePayments(
            features: List<BillingFeature> = listOf(),
            callback: (Boolean) -> Unit,
        )
    }

    /**
     * The passed in or generated app user ID.
     */
    public val appUserID: String

    /**
     * The delegate is responsible for handling promotional product purchases (App Store only) and
     * changes to customer information.
     *
     * **Note:** If your delegate is not a singleton, make sure you set this back to null when
     * you're done to avoid memory leaks. For instance, if your delegate is tied to a screen, set
     * this to null when the user navigates away from the screen.
     */
    public var delegate: PurchasesDelegate?

    /**
     * If the [appUserID] has been generated by RevenueCat.
     */
    public val isAnonymous: Boolean

    /**
     * The currently configured store
     */
    public val store: Store

    /**
     * This method will send all the purchases to the RevenueCat backend. Call this when using your
     * own implementation for subscriptions anytime a sync is needed, such as when migrating
     * existing users to RevenueCat. The [onSuccess] callback will be called if all purchases have
     * been synced successfully or there are no  Otherwise, the [onError] callback will be called
     * with a [PurchasesError] indicating the first error found.
     *
     * **Warning:** This function should only be called if you're migrating to RevenueCat or in
     * observer mode.
     *
     * **Warning:** This function could take a relatively long time to execute, depending on the
     * amount of purchases the user has. Consider that when waiting for this operation to complete.
     */
    public fun syncPurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (CustomerInfo) -> Unit,
    )

    /**
     * This method will send an Amazon purchase to the RevenueCat backend. This function should
     * only be called if you have set [PurchasesAreCompletedBy] to
     * [MyApp][PurchasesAreCompletedBy.MyApp] or when performing a client side migration of your
     * current users to RevenueCat.
     *
     * The receipt IDs are cached if successfully posted so they are not posted more than once.
     *
     * @param [productID] Product ID associated to the purchase.
     * @param [receiptID] ReceiptId that represents the Amazon purchase.
     * @param [amazonUserID] Amazon's userID. This parameter will be ignored when syncing a Google
     * purchase.
     * @param [isoCurrencyCode] Product's currency code in ISO 4217 format.
     * @param [price] Product's price.
     */
    public fun syncAmazonPurchase(
        productID: String,
        receiptID: String,
        amazonUserID: String,
        isoCurrencyCode: String?,
        price: Double?,
    )

    /**
     * Syncs subscriber attributes and then fetches the configured offerings for this user. This
     * method is intended to be called when using Targeting Rules with Custom Attributes. Any
     * subscriber attributes should be set before calling this method to ensure the returned
     * offerings are applied with the latest subscriber attributes.
     *
     * This method is rate limited to 5 calls per minute. It will log a warning and return offerings
     * cache when reached.
     *
     * Refer to [the guide](https://www.revenuecat.com/docs/tools/targeting) for more targeting
     * information.
     * For more offerings information, see [getOfferings].
     *
     * @param [onError] Called when there was an error syncing attributes or fetching offerings.
     * Will return the first error found syncing the
     * @param [onSuccess] Called when all attributes are synced and offerings are fetched.
     */
    public fun syncAttributesAndOfferingsIfNeeded(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offerings: Offerings) -> Unit,
    )

    /**
     * Fetch the configured offerings for this users. Offerings allows you to configure your in-app
     * products vis RevenueCat and greatly simplifies management. See
     * [the guide](https://docs.revenuecat.com/offerings) for more info.
     *
     * Offerings will be fetched and cached on instantiation so that, by the time they are needed,
     * your prices are loaded for your purchase flow. Time is money.
     */
    public fun getOfferings(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offerings: Offerings) -> Unit,
    )

    /**
     * Gets the [StoreProduct]s for the given list of product ids for all product types.
     */
    public fun getProducts(
        productIds: List<String>,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (storeProducts: List<StoreProduct>) -> Unit,
    )

    /**
     * App Store only. Use this method to fetch a [PromotionalOffer] to use with
     * [purchase].
     */
    public fun getPromotionalOffer(
        discount: StoreProductDiscount,
        storeProduct: StoreProduct,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offer: PromotionalOffer) -> Unit,
    )

    /**
     * Purchases [storeProduct].
     * On the Play Store, if [storeProduct] represents a subscription, upgrades from the
     * subscription specified by [oldProductId] and chooses [storeProduct]'s default
     * [SubscriptionOption].
     *
     * The default [SubscriptionOption] logic:
     *   - Filters out offers with `"rc-ignore-offer"` tag
     *   - Uses [SubscriptionOption] WITH longest free trial or cheapest first phase
     *   - Falls back to use base plan
     *
     * If [storeProduct] represents a non-subscription, [oldProductId] and [replacementMode] will be
     * ignored.
     *
     * @param storeProduct The [StoreProduct] you wish to purchase.
     * @param onError Will be called if the purchase has completed with an error, providing the
     * [PurchasesError] and a boolean indicating whether the user canceled the purchase.
     * @param onSuccess Will be called if the purchase has completed successfully, providing the
     * [StoreTransaction] and updated [CustomerInfo].
     * @param isPersonalizedPrice Play Store only, ignored otherwise. Optional boolean indicates
     * personalized pricing on products available for purchase in the EU. For compliance with EU
     * regulations. User will see "This price has been customize for you" in the purchase dialog
     * when true. See
     * [developer.android.com](https://developer.android.com/google/play/billing/integrate#personalized-price)
     * for more info.
     * @param oldProductId Play Store only, ignored otherwise. If this purchase is an upgrade from
     * another product, provide the previous product ID here.
     * @param replacementMode Play Store only, ignored otherwise. The replacement mode to use when
     * upgrading from another product. This field is ignored, unless [oldProductId] is non-null.
     */
    public fun purchase(
        storeProduct: StoreProduct,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean? = null,
        oldProductId: String? = null,
        replacementMode: ReplacementMode? = null,
    )

    /**
     * Purchases [packageToPurchase].
     * On the Play Store, if [packageToPurchase] represents a subscription, upgrades from the
     * subscription specified by [oldProductId] and chooses the default [SubscriptionOption] from
     * [packageToPurchase].
     *
     * The default [SubscriptionOption] logic:
     *   - Filters out offers with `"rc-ignore-offer"` tag
     *   - Uses [SubscriptionOption] WITH longest free trial or cheapest first phase
     *   - Falls back to use base plan
     *
     * If [packageToPurchase] represents a non-subscription, [oldProductId] and [replacementMode]
     * will be ignored.
     *
     * @param packageToPurchase The [Package] you wish to purchase.
     * @param onError Will be called if the purchase has completed with an error, providing the
     * [PurchasesError] and a boolean indicating whether the user canceled the purchase.
     * @param onSuccess Will be called if the purchase has completed successfully, providing the
     * [StoreTransaction] and updated [CustomerInfo].
     * @param isPersonalizedPrice Play Store only, ignored otherwise. Optional boolean indicates
     * personalized pricing on products available for purchase in the EU. For compliance with EU
     * regulations. User will see "This price has been customize for you" in the purchase dialog
     * when true. See
     * [developer.android.com](https://developer.android.com/google/play/billing/integrate#personalized-price)
     * for more info.
     * @param oldProductId Play Store only, ignored otherwise. If this purchase is an upgrade from
     * another product, provide the previous product ID here.
     * @param replacementMode Play Store only, ignored otherwise. The replacement mode to use when
     * upgrading from another product. This field is ignored, unless [oldProductId] is non-null.
     */
    public fun purchase(
        packageToPurchase: Package,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean? = null,
        oldProductId: String? = null,
        replacementMode: ReplacementMode? = null,
    )

    /**
     * Play Store only. Purchases [subscriptionOption].
     *
     * @param subscriptionOption The [SubscriptionOption] you wish to purchase.
     * @param onError Will be called if the purchase has completed with an error, providing the
     * [PurchasesError] and a boolean indicating whether the user canceled the purchase.
     * @param onSuccess Will be called if the purchase has completed successfully, providing the
     * [StoreTransaction] and updated [CustomerInfo].
     * @param isPersonalizedPrice Play Store only, ignored otherwise. Optional boolean indicates
     * personalized pricing on products available for purchase in the EU. For compliance with EU
     * regulations. User will see "This price has been customize for you" in the purchase dialog
     * when true. See
     * [developer.android.com](https://developer.android.com/google/play/billing/integrate#personalized-price)
     * for more info.
     * @param oldProductId Play Store only, ignored otherwise. If this purchase is an upgrade from
     * another product, provide the previous product ID here.
     * @param replacementMode Play Store only, ignored otherwise. The replacement mode to use when
     * upgrading from another product. This field is ignored, unless [oldProductId] is non-null.
     */
    public fun purchase(
        subscriptionOption: SubscriptionOption,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean? = null,
        oldProductId: String? = null,
        replacementMode: ReplacementMode? = null,
    )

    /**
     * App Store only. Use this function if you are not using the Offerings system to purchase a
     * [StoreProduct] with an applied PromotionalOffer. If you are using the Offerings system, use
     * the overload with a [Package] parameter instead.
     *
     * @param storeProduct The [StoreProduct] you wish to purchase.
     * @param promotionalOffer The [PromotionalOffer] to apply to this purchase.
     * @param onError Will be called if the purchase has completed with an error, providing the
     * [PurchasesError] and a boolean indicating whether the user canceled the purchase.
     * @param onSuccess Will be called if the purchase has completed successfully, providing the
     * [StoreTransaction] and updated [CustomerInfo].
     *
     * @see [getPromotionalOffer]
     */
    public fun purchase(
        storeProduct: StoreProduct,
        promotionalOffer: PromotionalOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    )

    /**
     * App Store only. Purchases [packageToPurchase]. Call this method when a user has decided to
     * purchase a product with an applied discount. Only call this in direct response to user input.
     * From here [Purchases] will handle the purchase with StoreKit and call either [onSuccess] or
     * [onError].
     *
     * @param packageToPurchase The [Package] you wish to purchase.
     * @param promotionalOffer The [PromotionalOffer] to apply to this purchase.
     * @param onError Will be called if the purchase has completed with an error, providing the
     * [PurchasesError] and a boolean indicating whether the user canceled the purchase.
     * @param onSuccess Will be called if the purchase has completed successfully, providing the
     * [StoreTransaction] and updated [CustomerInfo].
     *
     * @see [getPromotionalOffer]
     */
    public fun purchase(
        packageToPurchase: Package,
        promotionalOffer: PromotionalOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    )

    /**
     * Restores purchases made with the current Store account for the current user. This method will
     * post all purchases associated with the current Store account to RevenueCat and become
     * associated with the current [appUserID]. If the receipt token is being used by an existing
     * user, the current [appUserID] will be aliased together with the [appUserID] of the existing
     * user. Going forward, either [appUserID] will be able to reference the same user.
     *
     * You shouldn't use this method if you have your own account system. In that case "restoration"
     * is provided by your app passing the same [appUserID] used to purchase originally.
     */
    public fun restorePurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    )

    /**
     * iOS only. Always returns an error on iOS < 15.
     *
     * Use this method only if you already have your own IAP implementation using StoreKit 2 and
     * want to use RevenueCat's backend. If you are using StoreKit 1 for your implementation, you
     * do not need this method.
     *
     * You only need to use this method with *new* purchases.
     * Subscription updates are observed automatically.
     *
     * Important: This should only be used if you have set [PurchasesAreCompletedBy] to
     * [PurchasesAreCompletedBy.MyApp] during SDK configuration.
     *
     * **Warning** You need to finish the transaction yourself after calling this method.
     *
     * @param productID: The Product ID that was just purchased
     * @param onError Will be called if an error occurs, providing a [PurchasesError] describing
     * what went wrong.
     * @param onSuccess Will be called if the function completes successfully, including details
     * on the [StoreTransaction] that was recorded.
     */
    public fun recordPurchase(
        productID: String,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction) -> Unit,
    )

    /**
     * This function will change the current [appUserID]. Typically this would be used after a log
     * out to identify a new user without calling `configure()`.
     * @param newAppUserID The new appUserID that should be linked to the currently user
     */
    public fun logIn(
        newAppUserID: String,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit
    )

    /**
     * Resets the Purchases client clearing the save [appUserID]. This will generate a random user
     * id and save it in the cache.
     */
    public fun logOut(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    )

    /**
     * Call this when you are done with this instance of [Purchases].
     */
    public fun close()

    /**
     * Get the latest available customer info.
     *
     * @param fetchPolicy Specifies cache behavior for customer info retrieval.
     */
    public fun getCustomerInfo(
        fetchPolicy: CacheFetchPolicy = CacheFetchPolicy.default(),
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    )

    /**
     * Google Play and App Store only, no-op for Amazon.
     * Displays the specified in-app message types to the user as a snackbar if there are any
     * available to be shown.
     * If [PurchasesConfiguration.showInAppMessagesAutomatically] is enabled, this will be done
     * automatically.
     *
     * For more info:
     * - [Google Play](https://rev.cat/googleplayinappmessaging)
     * - [App Store](https://rev.cat/storekit-message)
     */
    public fun showInAppMessagesIfNeeded(
        messageTypes: List<StoreMessageType> = listOf(StoreMessageType.BILLING_ISSUES),
    )

    /**
     * Invalidates the cache for customer information.
     *
     * Most apps will not need to use this method; invalidating the cache can leave your app in an
     * invalid state. Refer to [the documentation](https://rev.cat/customer-info-cache) for more
     * information on using the cache properly.
     *
     * This is useful for cases where purchaser information might have been updated outside of the
     * app, like if a promotional subscription is granted through the RevenueCat dashboard.
     */
    public fun invalidateCustomerInfoCache()

    /**
     * Subscriber attributes are useful for storing additional, structured information on a user.
     * Since attributes are writable using a public key they should not be used for
     * managing secure or sensitive information such as subscription status, coins, etc.
     *
     * Key names starting with "$" are reserved names used by RevenueCat. For a full list of key
     * restrictions refer to our [guide](https://docs.revenuecat.com/docs/subscriber-attributes).
     *
     * @param attributes Map of attributes by key. Set the value as null to delete an attribute.
     */
    public fun setAttributes(attributes: Map<String, String?>)

    /**
     * Subscriber attribute associated with the Email address for the user
     *
     * @param email Null or empty will delete the subscriber attribute.
     */
    public fun setEmail(email: String?)

    /**
     * Subscriber attribute associated with the phone number for the user
     *
     * @param phoneNumber Null or empty will delete the subscriber attribute.
     */
    public fun setPhoneNumber(phoneNumber: String?)

    /**
     * Subscriber attribute associated with the display name for the user
     *
     * @param displayName Null or empty will delete the subscriber attribute.
     */
    public fun setDisplayName(displayName: String?)

    /**
     * Subscriber attribute associated with the push token for the user
     *
     * @param fcmToken Null or empty will delete the subscriber attribute.
     */
    public fun setPushToken(fcmToken: String?)

    /**
     * Subscriber attribute associated with the Mixpanel Distinct ID for the user
     *
     * @param mixpanelDistinctID null or an empty string will delete the subscriber attribute.
     */
    public fun setMixpanelDistinctID(mixpanelDistinctID: String?)

    /**
     * Subscriber attribute associated with the OneSignal Player Id for the user
     * Required for the RevenueCat OneSignal integration. Deprecated for OneSignal versions above
     * v9.0.
     *
     * @param onesignalID null or an empty string will delete the subscriber attribute
     */
    public fun setOnesignalID(onesignalID: String?)

    /**
     * Subscriber attribute associated with the OneSignal User ID for the user
     * Required for the RevenueCat OneSignal integration with versions v11.0 and above.
     *
     * @param onesignalUserID null or an empty string will delete the subscriber attribute
     */
    public fun setOnesignalUserID(onesignalUserID: String?)

    /**
     * Subscriber attribute associated with the Airship Channel ID
     * Required for the RevenueCat Airship integration
     *
     * @param airshipChannelID null or an empty string will delete the subscriber attribute
     */
    public fun setAirshipChannelID(airshipChannelID: String?)

    /**
     * Subscriber attribute associated with the Firebase App Instance ID for the user
     * Required for the RevenueCat Firebase integration
     *
     * @param firebaseAppInstanceID null or an empty string will delete the subscriber attribute.
     */
    public fun setFirebaseAppInstanceID(firebaseAppInstanceID: String?)

    /**
     * Automatically collect subscriber attributes associated with the device identifiers
     * `$gpsAdId`, `$androidId`, `$ip`
     *
     * **Warning:** In accordance with
     * [Google Play's data safety guidelines](https://rev.cat/google-plays-data-safety), you should
     * not be calling this function if your app targets children.
     *
     * **Warning:** You must declare the [AD_ID Permission](https://rev.cat/google-advertising-id)
     * when your app targets Android 13 or above. Apps that don’t declare the permission will get a
     * string of zeros.
     */
    public fun collectDeviceIdentifiers()

    /**
     * Subscriber attribute associated with the Adjust Id for the user
     * Required for the RevenueCat Adjust integration
     *
     * @param adjustID null or an empty string will delete the subscriber attribute
     */
    public fun setAdjustID(adjustID: String?)

    /**
     * Subscriber attribute associated with the AppsFlyer Id for the user
     * Required for the RevenueCat AppsFlyer integration
     *
     * @param appsflyerID null or an empty string will delete the subscriber attribute
     */
    public fun setAppsflyerID(appsflyerID: String?)

    /**
     * Subscriber attribute associated with the Facebook SDK Anonymous Id for the user
     * Recommended for the RevenueCat Facebook integration
     *
     * @param fbAnonymousID null or an empty string will delete the subscriber attribute
     */
    public fun setFBAnonymousID(fbAnonymousID: String?)

    /**
     * Subscriber attribute associated with the mParticle Id for the user
     * Recommended for the RevenueCat mParticle integration
     *
     * @param mparticleID null or an empty string will delete the subscriber attribute
     */
    public fun setMparticleID(mparticleID: String?)

    /**
     * Subscriber attribute associated with the CleverTap ID for the user
     * Required for the RevenueCat CleverTap integration
     *
     * @param cleverTapID null or an empty string will delete the subscriber attribute.
     */
    public fun setCleverTapID(cleverTapID: String?)

    /**
     * Subscriber attribute associated with the install media source for the user
     *
     * @param mediaSource null or an empty string will delete the subscriber attribute.
     */
    public fun setMediaSource(mediaSource: String?)

    /**
     * Subscriber attribute associated with the install campaign for the user
     *
     * @param campaign null or an empty string will delete the subscriber attribute.
     */
    public fun setCampaign(campaign: String?)

    /**
     * Subscriber attribute associated with the install ad group for the user
     *
     * @param adGroup null or an empty string will delete the subscriber attribute.
     */
    public fun setAdGroup(adGroup: String?)

    /**
     * Subscriber attribute associated with the install ad for the user
     *
     * @param ad null or an empty string will delete the subscriber attribute.
     */
    public fun setAd(ad: String?)

    /**
     * Subscriber attribute associated with the install keyword for the user
     *
     * @param keyword null or an empty string will delete the subscriber attribute.
     */
    public fun setKeyword(keyword: String?)

    /**
     * Subscriber attribute associated with the install ad creative for the user
     *
     * @param creative null or an empty string will delete the subscriber attribute.
     */
    public fun setCreative(creative: String?)

}

/**
 * Current version of the Purchases SDK
 */
public val Purchases.Companion.frameworkVersion: String
    get() = BuildKonfig.revenuecatKmpVersion

/**
 * Configures an instance of the SDK with the specified [configuration builder][builder]. The
 * instance will be set as a singleton. You should access the singleton instance using
 * [sharedInstance][Purchases.sharedInstance].
 */
@JvmSynthetic
public fun Purchases.Companion.configure(
    apiKey: String,
    builder: Builder.() -> Unit = { }
): Purchases =
    configure(PurchasesConfiguration(apiKey, builder))
