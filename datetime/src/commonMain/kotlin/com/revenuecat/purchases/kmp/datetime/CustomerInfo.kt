package com.revenuecat.purchases.kmp.datetime

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomerInfo
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Map of productIds to expiration instants.
 *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
 *  * For Amazon subscriptions, productsIds are `termSku`.
 */
@OptIn(ExperimentalTime::class)
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
@OptIn(ExperimentalTime::class)
public val CustomerInfo.allPurchaseInstants: Map<String, Instant?>
    get() = allPurchaseDateMillis.mapValues { (_, millis) ->
        millis?.let { Instant.fromEpochMilliseconds(it) }
    }

/**
 * The instant this user was first seen in RevenueCat.
 */
@OptIn(ExperimentalTime::class)
public val CustomerInfo.firstSeenInstant: Instant
    get() = Instant.fromEpochMilliseconds(firstSeenMillis)

/**
 * The latest expiration instant of all purchased productIds.
 */
@OptIn(ExperimentalTime::class)
public val CustomerInfo.latestExpirationInstant: Instant?
    get() = latestExpirationDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The purchase instant for the version of the application when the user bought the app. Use this
 * for grandfathering users when migrating to subscriptions. This can be null, see
 * [Purchases.restorePurchases].
 */
@OptIn(ExperimentalTime::class)
public val CustomerInfo.originalPurchaseInstant: Instant?
    get() = originalPurchaseDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The instant when this info was requested.
 */
@OptIn(ExperimentalTime::class)
public val CustomerInfo.requestInstant: Instant
    get() = Instant.fromEpochMilliseconds(requestDateMillis)
