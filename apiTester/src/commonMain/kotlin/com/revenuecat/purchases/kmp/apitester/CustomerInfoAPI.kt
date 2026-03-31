@file:Suppress("unused", "UNUSED_VARIABLE")

package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.EntitlementInfos
import com.revenuecat.purchases.kmp.models.SubscriptionInfo
import com.revenuecat.purchases.kmp.models.Transaction
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private class CustomerInfoAPI {
    @OptIn(ExperimentalTime::class)
    fun check(customerInfo: CustomerInfo) {
        with(customerInfo) {
            val entitlementInfo: EntitlementInfos = entitlements
            val asubs = activeSubscriptions
            val productIds: Set<String> = allPurchasedProductIdentifiers
            val ledm: Long? = latestExpirationDateMillis
            val led: Instant? = latestExpirationDate
            val si: Map<String, SubscriptionInfo> = subscriptionsByProductIdentifier
            val nst: List<Transaction> = nonSubscriptionTransactions
            val opdm: Long? = originalPurchaseDateMillis
            val opd: Instant? = originalPurchaseDate
            val rdm: Long = requestDateMillis
            val rd: Instant = requestDate
            val fsm: Long = firstSeenMillis
            val fs: Instant = firstSeen
            val oaui: String = originalAppUserId
            val mu: String? = managementUrlString
            val allExpirationMillisByProduct: Map<String, Long?> = allExpirationDateMillis
            val allExpirationDatesByProduct: Map<String, Instant?> = allExpirationDates
            val allPurchaseMillisByProduct: Map<String, Long?> = allPurchaseDateMillis
            val allPurchaseDatesByProduct: Map<String, Instant?> = allPurchaseDates
        }
    }
}
