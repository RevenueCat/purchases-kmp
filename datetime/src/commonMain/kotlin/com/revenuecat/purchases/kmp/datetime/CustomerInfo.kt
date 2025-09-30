package com.revenuecat.purchases.kmp.datetime

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomerInfo
import kotlinx.datetime.Instant

/**
 * Map of productIds to expiration instants.
 *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
 *  * For Amazon subscriptions, productsIds are `termSku`.
 */
@Deprecated(
    message = "Instant properties have been moved to member fields. The entire " +
            "purchases-kmp-datetime module is deprecated and can be removed from your Gradle " +
            "files.",
    replaceWith = ReplaceWith("allExpirationDates"),
)
public val CustomerInfo.allExpirationInstants: Map<String, Instant?>
    get() = allExpirationDateMillis.mapValues { (_, millis) ->
        millis?.let { Instant.fromEpochMilliseconds(it) }
    }

/**
 * Map of productIds to purchase instants.
 *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
 *  * For Google and Amazon INAPPs, productsIds are simply `productId`.
 *  * For Amazon subscriptions, productsIds are `termSku`.
 */
@Deprecated(
    message = "Instant properties have been moved to member fields. The entire " +
            "purchases-kmp-datetime module is deprecated and can be removed from your Gradle " +
            "files.",
    replaceWith = ReplaceWith("allPurchaseDates"),
)
public val CustomerInfo.allPurchaseInstants: Map<String, Instant?>
    get() = allPurchaseDateMillis.mapValues { (_, millis) ->
        millis?.let { Instant.fromEpochMilliseconds(it) }
    }

/**
 * The instant this user was first seen in RevenueCat.
 */
@Deprecated(
    message = "Instant properties have been moved to member fields. The entire " +
            "purchases-kmp-datetime module is deprecated and can be removed from your Gradle " +
            "files.",
    replaceWith = ReplaceWith("firstSeen"),
)
public val CustomerInfo.firstSeenInstant: Instant
    get() = Instant.fromEpochMilliseconds(firstSeenMillis)

/**
 * The latest expiration instant of all purchased productIds.
 */
@Deprecated(
    message = "Instant properties have been moved to member fields. The entire " +
            "purchases-kmp-datetime module is deprecated and can be removed from your Gradle " +
            "files.",
    replaceWith = ReplaceWith("latestExpirationDate"),
)
public val CustomerInfo.latestExpirationInstant: Instant?
    get() = latestExpirationDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The purchase instant for the version of the application when the user bought the app. Use this
 * for grandfathering users when migrating to subscriptions. This can be null, see
 * [Purchases.restorePurchases].
 */
@Deprecated(
    message = "Instant properties have been moved to member fields. The entire " +
            "purchases-kmp-datetime module is deprecated and can be removed from your Gradle " +
            "files.",
    replaceWith = ReplaceWith("originalPurchaseDate"),
)
public val CustomerInfo.originalPurchaseInstant: Instant?
    get() = originalPurchaseDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The instant when this info was requested.
 */
@Deprecated(
    message = "Instant properties have been moved to member fields. The entire " +
            "purchases-kmp-datetime module is deprecated and can be removed from your Gradle " +
            "files.",
    replaceWith = ReplaceWith("requestDate"),
)
public val CustomerInfo.requestInstant: Instant
    get() = Instant.fromEpochMilliseconds(requestDateMillis)
