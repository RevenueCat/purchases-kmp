package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.EntitlementInfo
import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kmp.models.PeriodType
import com.revenuecat.purchases.kmp.models.Store
import com.revenuecat.purchases.kn.core.RCAmazon
import com.revenuecat.purchases.kn.core.RCAppStore
import com.revenuecat.purchases.kn.core.RCBilling
import com.revenuecat.purchases.kn.core.RCEntitlementInfo
import com.revenuecat.purchases.kn.core.RCExternal
import com.revenuecat.purchases.kn.core.RCGalaxy
import com.revenuecat.purchases.kn.core.RCIntro
import com.revenuecat.purchases.kn.core.RCMacAppStore
import com.revenuecat.purchases.kn.core.RCNormal
import com.revenuecat.purchases.kn.core.RCPaddle
import com.revenuecat.purchases.kn.core.RCPlayStore
import com.revenuecat.purchases.kn.core.RCPrepaid
import com.revenuecat.purchases.kn.core.RCPromotional
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipTypeFamilyShared
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipTypePurchased
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipTypeUnknown
import com.revenuecat.purchases.kn.core.RCStripe
import com.revenuecat.purchases.kn.core.RCTestStore
import com.revenuecat.purchases.kn.core.RCTrial
import com.revenuecat.purchases.kn.core.RCUnknownStore
import com.revenuecat.purchases.kn.core.RCPeriodType as IosPeriodType
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipType as IosOwnershipType
import com.revenuecat.purchases.kn.core.RCStore as IosStore

internal fun RCEntitlementInfo.toEntitlementInfo(): EntitlementInfo {
    return EntitlementInfo(
        identifier = identifier(),
        isActive = isActive(),
        willRenew = willRenew(),
        periodType = periodType().toPeriodType(),
        latestPurchaseDateMillis = latestPurchaseDate()?.toEpochMilliseconds(),
        originalPurchaseDateMillis = originalPurchaseDate()?.toEpochMilliseconds(),
        expirationDateMillis = expirationDate()?.toEpochMilliseconds(),
        store = store().toStore(),
        productIdentifier = productIdentifier(),
        productPlanIdentifier = productPlanIdentifier(),
        isSandbox = isSandbox(),
        unsubscribeDetectedAtMillis = unsubscribeDetectedAt()?.toEpochMilliseconds(),
        billingIssueDetectedAtMillis = billingIssueDetectedAt()?.toEpochMilliseconds(),
        ownershipType = ownershipType().toOwnershipType(),
        verification = verification().toVerificationResult()
    )
}

internal fun IosStore.toStore(): Store =
    when (this) {
        RCAppStore -> Store.APP_STORE
        RCMacAppStore -> Store.MAC_APP_STORE
        RCPlayStore -> Store.PLAY_STORE
        RCStripe -> Store.STRIPE
        RCPromotional -> Store.PROMOTIONAL
        RCUnknownStore -> Store.UNKNOWN_STORE
        RCAmazon -> Store.AMAZON
        RCBilling -> Store.RC_BILLING
        RCExternal -> Store.EXTERNAL
        RCPaddle -> Store.PADDLE
        RCTestStore -> Store.TEST_STORE
        RCGalaxy -> Store.GALAXY
        else -> error("Unknown IosStore: $this")
    }

internal fun IosPeriodType.toPeriodType(): PeriodType =
    when (this) {
        RCNormal -> PeriodType.NORMAL
        RCIntro -> PeriodType.INTRO
        RCTrial -> PeriodType.TRIAL
        RCPrepaid -> PeriodType.PREPAID
        else -> error("Unknown IosPeriodType: $this")
    }

internal fun IosOwnershipType.toOwnershipType(): OwnershipType =
    when (this) {
        RCPurchaseOwnershipTypePurchased -> OwnershipType.PURCHASED
        RCPurchaseOwnershipTypeFamilyShared -> OwnershipType.FAMILY_SHARED
        RCPurchaseOwnershipTypeUnknown -> OwnershipType.UNKNOWN
        else -> error("Unknown IosOwnershipType: $this")
    }
