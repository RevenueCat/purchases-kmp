package com.revenuecat.purchases.kmp.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Class containing all information regarding the customer.
 */
public class CustomerInfo(
    /**
     * Set of active subscription productIds.
     * * For Google subscriptions, productIds will be `subscriptionId:basePlanId`.
     * * For Amazon subscriptions, productIds will be `termSku`.
     */
    public val activeSubscriptions: Set<String>,

    /**
     * Map of productIds to expiration dates in milliseconds since the Unix epoch.
     *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
     *  * For Amazon subscriptions, productsIds are `termSku`.
     */
    public val allExpirationDateMillis: Map<String, Long?>,

    /**
     * Map of productIds to purchase dates in milliseconds since the Unix epoch.
     *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
     *  * For Google and Amazon INAPPs, productsIds are simply `productId`.
     *  * For Amazon subscriptions, productsIds are `termSku`.
     */
    public val allPurchaseDateMillis: Map<String, Long?>,

    /**
     * Set of purchased productIds, active and inactive.
     *
     * * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
     * * For Google and Amazon INAPPs, productsIds are simply `productId`.
     * * For Amazon subscriptions, productsIds are `termSku`.
     */
    public val allPurchasedProductIdentifiers: Set<String>,

    /**
     * Entitlements attached to this customer info.
     */
    public val entitlements: EntitlementInfos,

    /**
     * The date this user was first seen in RevenueCat in milliseconds since the Unix epoch.
     */
    public val firstSeenMillis: Long,

    /**
     * The latest expiration date of all purchased productIds in milliseconds since the Unix epoch.
     */
    public val latestExpirationDateMillis: Long?,

    /**
     * URL to manage the active subscription of the user. If this user has an active iOS subscription,
     * this will point to the App Store, if the user has an active Play Store subscription it will
     * point there. If there are no active subscriptions it will be null. If there are multiple for
     * different platforms, it will point to the Play Store on Android, and to the App Store on iOS.
     */
    public val managementUrlString: String?,

    /**
     * List of all non subscription transactions. Use this to fetch the history of
     * non-subscription purchases.
     */
    public val nonSubscriptionTransactions: List<Transaction>,

    /**
     * The original app User Id recorded for this user.
     */
    public val originalAppUserId: String,

    /**
     * App Store only. The build number (in iOS) or the marketing version (in macOS) for the version of
     * the application when the user bought the app. This corresponds to the value of `CFBundleVersion`
     * (in iOS) or `CFBundleShortVersionString` (in macOS) in the `Info.plist` file when the purchase
     * was originally made. Use this for grandfathering users when migrating to subscriptions.
     */
    public val originalApplicationVersion: String?,

    /**
     * The purchase date in milliseconds since the Unix epoch for the version of the application when
     * the user bought the app. Use this for grandfathering users when migrating to subscriptions. This
     * can be null, see [Purchases.restorePurchases].
     */
    public val originalPurchaseDateMillis: Long?,

    /**
     * Date when this info was requested in milliseconds since the Unix epoch.
     */
    public val requestDateMillis: Long,
) {


    /**
     * Map of productIds to expiration dates.
     *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
     *  * For Amazon subscriptions, productsIds are `termSku`.
     */
    @ExperimentalTime
    public val allExpirationDates: Map<String, Instant?> by lazy {
        allExpirationDateMillis.mapValues { (_, millis) ->
            millis?.let { Instant.fromEpochMilliseconds(it) }
        }
    }

    /**
     * Map of productIds to purchase dates.
     *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
     *  * For Google and Amazon INAPPs, productsIds are simply `productId`.
     *  * For Amazon subscriptions, productsIds are `termSku`.
     */
    @ExperimentalTime
    public val allPurchaseDates: Map<String, Instant?> by lazy {
        allPurchaseDateMillis.mapValues { (_, millis) ->
            millis?.let { Instant.fromEpochMilliseconds(it) }
        }
    }

    /**
     * The moment this user was first seen in RevenueCat.
     */
    @ExperimentalTime
    public val firstSeen: Instant by lazy {
        Instant.fromEpochMilliseconds(firstSeenMillis)
    }

    /**
     * The latest expiration instant of all purchased productIds.
     */
    @ExperimentalTime
    public val latestExpirationDate: Instant? by lazy {
        latestExpirationDateMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * The purchase date for the version of the application when the user bought the app. Use this
     * for grandfathering users when migrating to subscriptions. This can be null, see
     * `Purchases.restorePurchases`.
     */
    @ExperimentalTime
    public val originalPurchaseDate: Instant? by lazy {
        originalPurchaseDateMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * The moment when this info was requested.
     */
    @ExperimentalTime
    public val requestDate: Instant by lazy {
        Instant.fromEpochMilliseconds(requestDateMillis)
    }

}
