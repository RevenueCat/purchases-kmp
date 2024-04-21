@file:Suppress("unused", "UNUSED_VARIABLE")

package io.shortway.kobankat.apitester

import io.shortway.kobankat.CustomerInfo
import io.shortway.kobankat.EntitlementInfos
import io.shortway.kobankat.activeSubscriptions
import io.shortway.kobankat.allExpirationDateMillis
import io.shortway.kobankat.allPurchaseDateMillis
import io.shortway.kobankat.allPurchasedProductIdentifiers
import io.shortway.kobankat.datetime.allExpirationInstants
import io.shortway.kobankat.datetime.allPurchaseInstants
import io.shortway.kobankat.datetime.firstSeenInstant
import io.shortway.kobankat.datetime.latestExpirationInstant
import io.shortway.kobankat.datetime.originalPurchaseInstant
import io.shortway.kobankat.datetime.requestInstant
import io.shortway.kobankat.entitlements
import io.shortway.kobankat.firstSeenMillis
import io.shortway.kobankat.latestExpirationDateMillis
import io.shortway.kobankat.managementURL
import io.shortway.kobankat.models.Transaction
import io.shortway.kobankat.nonSubscriptionTransactions
import io.shortway.kobankat.originalAppUserId
import io.shortway.kobankat.originalPurchaseDateMillis
import io.shortway.kobankat.requestDateMillis
import kotlinx.datetime.Instant

private class CustomerInfoAPI {
    fun check(customerInfo: CustomerInfo) {
        with(customerInfo) {
            val entitlementInfo: EntitlementInfos = entitlements
            val asubs = activeSubscriptions
            val productIds: Set<String> = allPurchasedProductIdentifiers
            val led: Long? = latestExpirationDateMillis
            val lei: Instant? = latestExpirationInstant
            val nst: List<Transaction> = nonSubscriptionTransactions
            val opd: Long? = originalPurchaseDateMillis
            val opi: Instant? = originalPurchaseInstant
            val rd: Long = requestDateMillis
            val ri: Instant = requestInstant
            val fsm: Long = firstSeenMillis
            val fsi: Instant = firstSeenInstant
            val oaui: String = originalAppUserId
            val mu: String? = managementURL
            val allExpirationMillisByProduct: Map<String, Long?> = allExpirationDateMillis
            val allExpirationInstantsByProduct: Map<String, Instant?> = allExpirationInstants
            val allPurchaseMillisByProduct: Map<String, Long?> = allPurchaseDateMillis
            val allPurchaseInstantsByProduct: Map<String, Instant?> = allPurchaseInstants
        }
    }
}
