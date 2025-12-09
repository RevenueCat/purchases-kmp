@file:Suppress("unused", "UNUSED_VARIABLE")

package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kmp.models.PeriodType
import com.revenuecat.purchases.kmp.models.Store
import com.revenuecat.purchases.kmp.models.SubscriptionInfo
import com.revenuecat.purchases.kmp.models.SubscriptionPrice

private class SubscriptionInfoAPI {
    fun check(subscriptionInfo: SubscriptionInfo) {
        with(subscriptionInfo) {
            val pi: String = productIdentifier
            val pdm: Long = purchaseDateMillis
            val opdm: Long? = originalPurchaseDateMillis
            val edm: Long? = expiresDateMillis
            val s: Store = store
            val sb: Boolean = isSandbox
            val udam: Long? = unsubscribeDetectedAtMillis
            val bidam: Long? = billingIssuesDetectedAtMillis
            val gpedm: Long? = gracePeriodExpiresDateMillis
            val ot: OwnershipType = ownershipType
            val pt: PeriodType = periodType
            val ram: Long? = refundedAtMillis
            val sti: String? = storeTransactionId
            val ardm: Long? = autoResumeDateMillis
            val pr: SubscriptionPrice? = price
            val ppi: String? = productPlanIdentifier
            val mus: String? = managementUrlString
            val ia: Boolean = isActive
            val wr: Boolean = willRenew
        }
    }
}