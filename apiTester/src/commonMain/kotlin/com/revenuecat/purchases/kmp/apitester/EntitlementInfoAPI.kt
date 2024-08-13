package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.EntitlementInfo
import com.revenuecat.purchases.kmp.VerificationResult
import com.revenuecat.purchases.kmp.billingIssueDetectedAtMillis
import com.revenuecat.purchases.kmp.datetime.billingIssueDetectedAtInstant
import com.revenuecat.purchases.kmp.datetime.expirationInstant
import com.revenuecat.purchases.kmp.datetime.latestPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.originalPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.unsubscribeDetectedAtInstant
import com.revenuecat.purchases.kmp.expirationDateMillis
import com.revenuecat.purchases.kmp.identifier
import com.revenuecat.purchases.kmp.isActive
import com.revenuecat.purchases.kmp.isSandbox
import com.revenuecat.purchases.kmp.latestPurchaseDateMillis
import com.revenuecat.purchases.kmp.originalPurchaseDateMillis
import com.revenuecat.purchases.kmp.productIdentifier
import com.revenuecat.purchases.kmp.productPlanIdentifier
import com.revenuecat.purchases.kmp.unsubscribeDetectedAtMillis
import com.revenuecat.purchases.kmp.verification
import com.revenuecat.purchases.kmp.willRenew
import kotlinx.datetime.Instant

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
            val verification: VerificationResult = verification
        }
    }

}
