@file:OptIn(ExperimentalTime::class)

package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.datetime.billingIssueDetectedAtInstant
import com.revenuecat.purchases.kmp.datetime.expirationInstant
import com.revenuecat.purchases.kmp.datetime.latestPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.originalPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.unsubscribeDetectedAtInstant
import com.revenuecat.purchases.kmp.models.EntitlementInfo
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Suppress("unused", "UNUSED_VARIABLE")
private class EntitlementInfoAPI {
    fun check(entitlementInfo: EntitlementInfo) {
        with(entitlementInfo) {
            val identifier: String = identifier
            val active: Boolean = isActive
            val willRenew: Boolean = willRenew
            // FIXME re-enable in SDK-3530
            //  val periodType: PeriodType = periodType
            val latestPurchaseDate: Long? = latestPurchaseDateMillis
            val latestPurchaseInstant: Instant? = latestPurchaseInstant
            val originalPurchaseDate: Long? = originalPurchaseDateMillis
            val originalPurchaseInstant: Instant? = originalPurchaseInstant
            val expirationDate: Long? = expirationDateMillis
            val expirationInstant: Instant? = expirationInstant
            // FIXME re-enable in SDK-3530
            //  val store: Store = store
            val productIdentifier: String = productIdentifier
            val productPlanIdentifier: String? = productPlanIdentifier
            val sandbox: Boolean = isSandbox
            val unsubscribeDetectedAt: Long? = unsubscribeDetectedAtMillis
            val unsubscribeDetectedAtInstant: Instant? = unsubscribeDetectedAtInstant
            val billingIssueDetectedAt: Long? = billingIssueDetectedAtMillis
            val billingIssueDetectedAtInstant: Instant? = billingIssueDetectedAtInstant
            // FIXME re-enable in SDK-3530
            //  val ownershipType: OwnershipType = ownershipType
            // FIXME re-enable in SDK-3530
            //  val verification: VerificationResult = verification
        }
    }

}
