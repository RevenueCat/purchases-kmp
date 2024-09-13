package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.EntitlementInfo
import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kmp.models.PeriodType
import com.revenuecat.purchases.EntitlementInfo as RcEntitlementInfo
import com.revenuecat.purchases.OwnershipType as AndroidOwnershipType
import com.revenuecat.purchases.PeriodType as AndroidPeriodType

internal fun RcEntitlementInfo.toEntitlementInfo(): EntitlementInfo =
    EntitlementInfo(
        identifier = identifier,
        isActive = isActive,
        willRenew = willRenew,
        periodType = periodType.toPeriodType(),
        latestPurchaseDateMillis = latestPurchaseDate.time,
        originalPurchaseDateMillis = originalPurchaseDate.time,
        expirationDateMillis = expirationDate?.time,
        store = store.toStore(),
        productIdentifier = productIdentifier,
        productPlanIdentifier = productPlanIdentifier,
        isSandbox = isSandbox,
        unsubscribeDetectedAtMillis = unsubscribeDetectedAt?.time,
        billingIssueDetectedAtMillis = billingIssueDetectedAt?.time,
        ownershipType = ownershipType.toOwnershipType(),
        verification = verification.toVerificationResult()
    )

internal fun AndroidPeriodType.toPeriodType(): PeriodType =
    when (this) {
        AndroidPeriodType.NORMAL -> PeriodType.NORMAL
        AndroidPeriodType.INTRO -> PeriodType.INTRO
        AndroidPeriodType.TRIAL -> PeriodType.TRIAL
    }

internal fun AndroidOwnershipType.toOwnershipType(): OwnershipType =
    when (this) {
        AndroidOwnershipType.PURCHASED -> OwnershipType.PURCHASED
        AndroidOwnershipType.FAMILY_SHARED -> OwnershipType.FAMILY_SHARED
        AndroidOwnershipType.UNKNOWN -> OwnershipType.UNKNOWN

    }
