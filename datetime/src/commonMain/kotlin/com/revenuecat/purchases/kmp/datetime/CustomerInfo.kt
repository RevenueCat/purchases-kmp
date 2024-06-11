package com.revenuecat.purchases.kmp.datetime

import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.allExpirationDateMillis
import com.revenuecat.purchases.kmp.allPurchaseDateMillis
import com.revenuecat.purchases.kmp.firstSeenMillis
import com.revenuecat.purchases.kmp.latestExpirationDateMillis
import com.revenuecat.purchases.kmp.originalPurchaseDateMillis
import com.revenuecat.purchases.kmp.requestDateMillis
import com.revenuecat.purchases.kmp.restorePurchases
import kotlinx.datetime.Instant

/**
 * Map of productIds to expiration instants.
 *  * For Google subscriptions, productIds are `subscriptionId:basePlanId`.
 *  * For Amazon subscriptions, productsIds are `termSku`.
 */
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
public val CustomerInfo.allPurchaseInstants: Map<String, Instant?>
    get() = allPurchaseDateMillis.mapValues { (_, millis) ->
        millis?.let { Instant.fromEpochMilliseconds(it) }
    }

/**
 * The instant this user was first seen in RevenueCat.
 */
public val CustomerInfo.firstSeenInstant: Instant
    get() = Instant.fromEpochMilliseconds(firstSeenMillis)

/**
 * The latest expiration instant of all purchased productIds.
 */
public val CustomerInfo.latestExpirationInstant: Instant?
    get() = latestExpirationDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The purchase instant for the version of the application when the user bought the app. Use this
 * for grandfathering users when migrating to subscriptions. This can be null, see
 * [Purchases.restorePurchases].
 */
public val CustomerInfo.originalPurchaseInstant: Instant?
    get() = originalPurchaseDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The instant when this info was requested.
 */
public val CustomerInfo.requestInstant: Instant
    get() = Instant.fromEpochMilliseconds(requestDateMillis)
