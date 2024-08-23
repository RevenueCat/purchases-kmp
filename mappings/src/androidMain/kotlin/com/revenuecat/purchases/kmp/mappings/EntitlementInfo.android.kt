package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.EntitlementInfo
import com.revenuecat.purchases.kmp.OwnershipType
import com.revenuecat.purchases.kmp.PeriodType
import com.revenuecat.purchases.EntitlementInfo as RcEntitlementInfo
import com.revenuecat.purchases.OwnershipType as AndroidOwnershipType
import com.revenuecat.purchases.PeriodType as AndroidPeriodType

internal fun RcEntitlementInfo.toEntitlementInfo(): EntitlementInfo =
    EntitlementInfo(
        identifier,
        isActive,
        willRenew,
        periodType.toPeriodType(),
        latestPurchaseDate.time,
        originalPurchaseDate.time,
        expirationDate?.time,
        store.toStore(),
        productIdentifier,
        productPlanIdentifier,
        isSandbox,
        unsubscribeDetectedAt?.time,
        billingIssueDetectedAt?.time,
        ownershipType.toOwnershipType(),
        verification.toVerificationResult()
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
