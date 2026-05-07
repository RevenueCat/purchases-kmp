package com.revenuecat.purchases.kmp.apitester


import com.revenuecat.purchases.kmp.models.EntitlementInfo
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
            val latestPurchaseDate: Instant? = latestPurchaseDate
            val originalPurchaseDateMillis: Long? = originalPurchaseDateMillis
            val originalPurchaseDate: Instant? = originalPurchaseDate
            val expirationDateMillis: Long? = expirationDateMillis
            val expirationDate: Instant? = expirationDate
            // FIXME re-enable in SDK-3530
            //  val store: Store = store
            val productIdentifier: String = productIdentifier
            val productPlanIdentifier: String? = productPlanIdentifier
            val sandbox: Boolean = isSandbox
            val unsubscribeDetectedAtMillis: Long? = unsubscribeDetectedAtMillis
            val unsubscribeDetectedAt: Instant? = unsubscribeDetectedAt
            val billingIssueDetectedAtMillis: Long? = billingIssueDetectedAtMillis
            val billingIssueDetectedAt: Instant? = billingIssueDetectedAt
            // FIXME re-enable in SDK-3530
            //  val ownershipType: OwnershipType = ownershipType
            // FIXME re-enable in SDK-3530
            //  val verification: VerificationResult = verification
        }
    }

}
