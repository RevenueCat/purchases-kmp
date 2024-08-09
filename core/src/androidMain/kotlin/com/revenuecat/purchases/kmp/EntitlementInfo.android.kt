@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.EntitlementInfo as RcEntitlementInfo
import com.revenuecat.purchases.OwnershipType as RcOwnershipType
import com.revenuecat.purchases.PeriodType as RcPeriodType
import com.revenuecat.purchases.Store as RcStore

public actual typealias EntitlementInfo = RcEntitlementInfo

public actual val EntitlementInfo.identifier: String
    get() = identifier
public actual val EntitlementInfo.isActive: Boolean
    get() = isActive
public actual val EntitlementInfo.willRenew: Boolean
    get() = willRenew
public actual val EntitlementInfo.periodType: PeriodType
    get() = periodType
public actual val EntitlementInfo.latestPurchaseDateMillis: Long?
    get() = latestPurchaseDate.time
public actual val EntitlementInfo.originalPurchaseDateMillis: Long?
    get() = originalPurchaseDate.time
public actual val EntitlementInfo.expirationDateMillis: Long?
    get() = expirationDate?.time
public actual val EntitlementInfo.store: Store
    get() = store
public actual val EntitlementInfo.productIdentifier: String
    get() = productIdentifier
public actual val EntitlementInfo.productPlanIdentifier: String?
    get() = productPlanIdentifier
public actual val EntitlementInfo.isSandbox: Boolean
    get() = isSandbox
public actual val EntitlementInfo.unsubscribeDetectedAtMillis: Long?
    get() = unsubscribeDetectedAt?.time
public actual val EntitlementInfo.billingIssueDetectedAtMillis: Long?
    get() = billingIssueDetectedAt?.time
public actual val EntitlementInfo.ownershipType: OwnershipType
    get() = ownershipType
public actual val EntitlementInfo.verification: VerificationResult
    get() = verification

public actual typealias Store = RcStore
public actual typealias PeriodType = RcPeriodType
public actual typealias OwnershipType = RcOwnershipType
