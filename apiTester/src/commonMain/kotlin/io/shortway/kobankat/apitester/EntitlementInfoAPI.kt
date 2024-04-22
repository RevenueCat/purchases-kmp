package io.shortway.kobankat.apitester

import io.shortway.kobankat.EntitlementInfo
import io.shortway.kobankat.OwnershipType
import io.shortway.kobankat.PeriodType
import io.shortway.kobankat.Store
import io.shortway.kobankat.VerificationResult
import io.shortway.kobankat.billingIssueDetectedAtMillis
import io.shortway.kobankat.datetime.billingIssueDetectedAtInstant
import io.shortway.kobankat.datetime.expirationInstant
import io.shortway.kobankat.datetime.latestPurchaseInstant
import io.shortway.kobankat.datetime.originalPurchaseInstant
import io.shortway.kobankat.datetime.unsubscribeDetectedAtInstant
import io.shortway.kobankat.expirationDateMillis
import io.shortway.kobankat.identifier
import io.shortway.kobankat.isActive
import io.shortway.kobankat.isSandbox
import io.shortway.kobankat.latestPurchaseDateMillis
import io.shortway.kobankat.originalPurchaseDateMillis
import io.shortway.kobankat.ownershipType
import io.shortway.kobankat.periodType
import io.shortway.kobankat.productIdentifier
import io.shortway.kobankat.productPlanIdentifier
import io.shortway.kobankat.store
import io.shortway.kobankat.unsubscribeDetectedAtMillis
import io.shortway.kobankat.verification
import io.shortway.kobankat.willRenew
import kotlinx.datetime.Instant

@Suppress("unused", "UNUSED_VARIABLE")
private class EntitlementInfoAPI {
    fun check(entitlementInfo: EntitlementInfo) {
        with(entitlementInfo) {
            val identifier: String = identifier
            val active: Boolean = isActive
            val willRenew: Boolean = willRenew
            val periodType: PeriodType = periodType
            val latestPurchaseDate: Long? = latestPurchaseDateMillis
            val latestPurchaseInstant: Instant? = latestPurchaseInstant
            val originalPurchaseDate: Long? = originalPurchaseDateMillis
            val originalPurchaseInstant: Instant? = originalPurchaseInstant
            val expirationDate: Long? = expirationDateMillis
            val expirationInstant: Instant? = expirationInstant
            val store: Store = store
            val productIdentifier: String = productIdentifier
            val productPlanIdentifier: String? = productPlanIdentifier
            val sandbox: Boolean = isSandbox
            val unsubscribeDetectedAt: Long? = unsubscribeDetectedAtMillis
            val unsubscribeDetectedAtInstant: Instant? = unsubscribeDetectedAtInstant
            val billingIssueDetectedAt: Long? = billingIssueDetectedAtMillis
            val billingIssueDetectedAtInstant: Instant? = billingIssueDetectedAtInstant
            val ownershipType: OwnershipType = ownershipType
            val verification: VerificationResult = verification
        }
    }

}
