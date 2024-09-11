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
            val mu: String? = managementUrlString
            val allExpirationMillisByProduct: Map<String, Long?> = allExpirationDateMillis
            val allExpirationInstantsByProduct: Map<String, Instant?> = allExpirationInstants
            val allPurchaseMillisByProduct: Map<String, Long?> = allPurchaseDateMillis
            val allPurchaseInstantsByProduct: Map<String, Instant?> = allPurchaseInstants
        }
    }
}
