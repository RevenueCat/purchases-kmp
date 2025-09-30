package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.datetime.billingIssueDetectedAtInstant
import com.revenuecat.purchases.kmp.datetime.expirationInstant
import com.revenuecat.purchases.kmp.datetime.latestPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.originalPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.unsubscribeDetectedAtInstant
import com.revenuecat.purchases.kmp.models.EntitlementInfo
import kotlin.time.ExperimentalTime

@Suppress("unused", "UNUSED_VARIABLE")
private class EntitlementInfoAPI {
    @OptIn(ExperimentalTime::class)
    fun check(entitlementInfo: EntitlementInfo) {
        with(entitlementInfo) {
            val identifier: String = identifier
            val active: Boolean = isActive
            val willRenew: Boolean = willRenew
            // FIXME re-enable in SDK-3530
            //  val periodType: PeriodType = periodType
            val latestPurchaseDateMillis: Long? = latestPurchaseDateMillis
            val latestPurchaseInstant: kotlinx.datetime.Instant? = latestPurchaseInstant
            val latestPurchaseDate: kotlin.time.Instant? = latestPurchaseDate
            val originalPurchaseDateMillis: Long? = originalPurchaseDateMillis
            val originalPurchaseInstant: kotlinx.datetime.Instant? = originalPurchaseInstant
            val originalPurchaseDate: kotlin.time.Instant? = originalPurchaseDate
            val expirationDateMillis: Long? = expirationDateMillis
            val expirationInstant: kotlinx.datetime.Instant? = expirationInstant
            val expirationDate: kotlin.time.Instant? = expirationDate
            // FIXME re-enable in SDK-3530
            //  val store: Store = store
            val productIdentifier: String = productIdentifier
            val productPlanIdentifier: String? = productPlanIdentifier
            val sandbox: Boolean = isSandbox
            val unsubscribeDetectedAtMillis: Long? = unsubscribeDetectedAtMillis
            val unsubscribeDetectedAtInstant: kotlinx.datetime.Instant? = unsubscribeDetectedAtInstant
            val unsubscribeDetectedAt: kotlin.time.Instant? = unsubscribeDetectedAt
            val billingIssueDetectedAtMillis: Long? = billingIssueDetectedAtMillis
            val billingIssueDetectedAtInstant: kotlinx.datetime.Instant? = billingIssueDetectedAtInstant
            val billingIssueDetectedAt: kotlin.time.Instant? = billingIssueDetectedAt
            // FIXME re-enable in SDK-3530
            //  val ownershipType: OwnershipType = ownershipType
            // FIXME re-enable in SDK-3530
            //  val verification: VerificationResult = verification
        }
    }

}
