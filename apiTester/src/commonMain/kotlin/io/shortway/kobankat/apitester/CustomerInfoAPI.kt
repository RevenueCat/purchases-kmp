@file:Suppress("unused", "UNUSED_VARIABLE")

package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.EntitlementInfos
import com.revenuecat.purchases.kmp.activeSubscriptions
import com.revenuecat.purchases.kmp.allExpirationDateMillis
import com.revenuecat.purchases.kmp.allPurchaseDateMillis
import com.revenuecat.purchases.kmp.allPurchasedProductIdentifiers
import com.revenuecat.purchases.kmp.entitlements
import com.revenuecat.purchases.kmp.firstSeenMillis
import com.revenuecat.purchases.kmp.latestExpirationDateMillis
import com.revenuecat.purchases.kmp.managementUrlString
import com.revenuecat.purchases.kmp.models.Transaction
import com.revenuecat.purchases.kmp.nonSubscriptionTransactions
import com.revenuecat.purchases.kmp.originalAppUserId
import com.revenuecat.purchases.kmp.originalPurchaseDateMillis
import com.revenuecat.purchases.kmp.requestDateMillis
import io.shortway.kobankat.datetime.allExpirationInstants
import io.shortway.kobankat.datetime.allPurchaseInstants
import io.shortway.kobankat.datetime.firstSeenInstant
import io.shortway.kobankat.datetime.latestExpirationInstant
import io.shortway.kobankat.datetime.originalPurchaseInstant
import io.shortway.kobankat.datetime.requestInstant
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
