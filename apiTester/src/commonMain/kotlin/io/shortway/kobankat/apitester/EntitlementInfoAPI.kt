package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.EntitlementInfo
import com.revenuecat.purchases.kmp.OwnershipType
import com.revenuecat.purchases.kmp.PeriodType
import com.revenuecat.purchases.kmp.Store
import com.revenuecat.purchases.kmp.VerificationResult
import com.revenuecat.purchases.kmp.billingIssueDetectedAtMillis
import com.revenuecat.purchases.kmp.expirationDateMillis
import com.revenuecat.purchases.kmp.identifier
import com.revenuecat.purchases.kmp.isActive
import com.revenuecat.purchases.kmp.isSandbox
import com.revenuecat.purchases.kmp.latestPurchaseDateMillis
import com.revenuecat.purchases.kmp.originalPurchaseDateMillis
import com.revenuecat.purchases.kmp.ownershipType
import com.revenuecat.purchases.kmp.periodType
import com.revenuecat.purchases.kmp.productIdentifier
import com.revenuecat.purchases.kmp.productPlanIdentifier
import com.revenuecat.purchases.kmp.store
import com.revenuecat.purchases.kmp.unsubscribeDetectedAtMillis
import com.revenuecat.purchases.kmp.verification
import com.revenuecat.purchases.kmp.willRenew
import io.shortway.kobankat.datetime.billingIssueDetectedAtInstant
import io.shortway.kobankat.datetime.expirationInstant
import io.shortway.kobankat.datetime.latestPurchaseInstant
import io.shortway.kobankat.datetime.originalPurchaseInstant
import io.shortway.kobankat.datetime.unsubscribeDetectedAtInstant
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
