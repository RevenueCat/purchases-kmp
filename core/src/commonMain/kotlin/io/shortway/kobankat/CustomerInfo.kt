package io.shortway.kobankat

import io.shortway.kobankat.models.Transaction

/**
 * Class containing all information regarding the customer.
 */
public expect class CustomerInfo

/**
 * Set of active subscription productIds.
 * * For Google subscriptions, productIds will be `subscriptionId:basePlanId`.
 * * For Amazon subscriptions, productIds will be `termSku`.
 */
public expect val CustomerInfo.activeSubscriptions: Set<String>

/**
 * Map of productIds to expiration dates in milliseconds since the Unix epoch.
 *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
 *  * For Amazon subscriptions, productsIds are `termSku`.
 */
public expect val CustomerInfo.allExpirationDateMillis: Map<String, Long?>

/**
 * Map of productIds to purchase dates in milliseconds since the Unix epoch.
 *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
 *  * For Google and Amazon INAPPs, productsIds are simply `productId`.
 *  * For Amazon subscriptions, productsIds are `termSku`.
 */
public expect val CustomerInfo.allPurchaseDateMillis: Map<String, Long?>

/**
 * Set of purchased productIds, active and inactive.
 *
 * * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
 * * For Google and Amazon INAPPs, productsIds are simply `productId`.
 * * For Amazon subscriptions, productsIds are `termSku`.
 */
public expect val CustomerInfo.allPurchasedProductIdentifiers: Set<String>

/**
 * Entitlements attached to this customer info.
 */
public expect val CustomerInfo.entitlements: EntitlementInfos

/**
 * The date this user was first seen in RevenueCat in milliseconds since the Unix epoch.
 */
public expect val CustomerInfo.firstSeenMillis: Long

/**
 * The latest expiration date of all purchased productIds in milliseconds since the Unix epoch.
 */
public expect val CustomerInfo.latestExpirationDateMillis: Long?

/**
 * URL to manage the active subscription of the user. If this user has an active iOS subscription,
 * this will point to the App Store, if the user has an active Play Store subscription it will
 * point there. If there are no active subscriptions it will be null. If there are multiple for
 * different platforms, it will point to the Play Store on Android, and to the App Store on iOS.
 */
public expect val CustomerInfo.managementUrlString: String?

/**
 * List of all non subscription transactions. Use this to fetch the history of
 * non-subscription purchases.
 */
public expect val CustomerInfo.nonSubscriptionTransactions: List<Transaction>

/**
 * The original app User Id recorded for this user.
 */
public expect val CustomerInfo.originalAppUserId: String

/**
 * App Store only. The build number (in iOS) or the marketing version (in macOS) for the version of
 * the application when the user bought the app. This corresponds to the value of `CFBundleVersion`
 * (in iOS) or `CFBundleShortVersionString` (in macOS) in the `Info.plist` file when the purchase
 * was originally made. Use this for grandfathering users when migrating to subscriptions.
 */
public expect val CustomerInfo.originalApplicationVersion: String?

/**
 * The purchase date in milliseconds since the Unix epoch for the version of the application when
 * the user bought the app. Use this for grandfathering users when migrating to subscriptions. This
 * can be null, see [Purchases.restorePurchases].
 */
public expect val CustomerInfo.originalPurchaseDateMillis: Long?

/**
 * Date when this info was requested in milliseconds since the Unix epoch.
 */
public expect val CustomerInfo.requestDateMillis: Long
