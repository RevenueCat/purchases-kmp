@file:Suppress("unused", "UNUSED_VARIABLE")

package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.datetime.allExpirationInstants
import com.revenuecat.purchases.kmp.datetime.allPurchaseInstants
import com.revenuecat.purchases.kmp.datetime.firstSeenInstant
import com.revenuecat.purchases.kmp.datetime.latestExpirationInstant
import com.revenuecat.purchases.kmp.datetime.originalPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.requestInstant
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.EntitlementInfos
import com.revenuecat.purchases.kmp.models.Transaction
import kotlin.time.ExperimentalTime

private class CustomerInfoAPI {
    @OptIn(ExperimentalTime::class)
    fun check(customerInfo: CustomerInfo) {
        with(customerInfo) {
            val entitlementInfo: EntitlementInfos = entitlements
            val asubs = activeSubscriptions
            val productIds: Set<String> = allPurchasedProductIdentifiers
            val ledm: Long? = latestExpirationDateMillis
            val lei: kotlinx.datetime.Instant? = latestExpirationInstant
            val led: kotlin.time.Instant? = latestExpirationDate
            val nst: List<Transaction> = nonSubscriptionTransactions
            val opdm: Long? = originalPurchaseDateMillis
            val opi: kotlinx.datetime.Instant? = originalPurchaseInstant
            val opd: kotlin.time.Instant? = originalPurchaseDate
            val rdm: Long = requestDateMillis
            val ri: kotlinx.datetime.Instant = requestInstant
            val rd: kotlin.time.Instant = requestDate
            val fsm: Long = firstSeenMillis
            val fsi: kotlinx.datetime.Instant = firstSeenInstant
            val fs: kotlin.time.Instant = firstSeen
            val oaui: String = originalAppUserId
            val mu: String? = managementUrlString
            val allExpirationMillisByProduct: Map<String, Long?> = allExpirationDateMillis
            val allExpirationInstantsByProduct: Map<String, kotlinx.datetime.Instant?> = allExpirationInstants
            val allExpirationDatesByProduct: Map<String, kotlin.time.Instant?> = allExpirationDates
            val allPurchaseMillisByProduct: Map<String, Long?> = allPurchaseDateMillis
            val allPurchaseInstantsByProduct: Map<String, kotlinx.datetime.Instant?> = allPurchaseInstants
            val allPurchaseDatesByProduct: Map<String, kotlin.time.Instant?> = allPurchaseDates
        }
    }
}
