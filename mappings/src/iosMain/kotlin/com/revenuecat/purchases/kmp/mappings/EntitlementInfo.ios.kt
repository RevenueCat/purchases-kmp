package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCAmazon
import swiftPMImport.com.revenuecat.purchases.models.RCAppStore
import swiftPMImport.com.revenuecat.purchases.models.RCBilling
import swiftPMImport.com.revenuecat.purchases.models.RCEntitlementInfo
import swiftPMImport.com.revenuecat.purchases.models.RCIntro
import swiftPMImport.com.revenuecat.purchases.models.RCMacAppStore
import swiftPMImport.com.revenuecat.purchases.models.RCNormal
import swiftPMImport.com.revenuecat.purchases.models.RCPaddle
import swiftPMImport.com.revenuecat.purchases.models.RCPrepaid
import swiftPMImport.com.revenuecat.purchases.models.RCPlayStore
import swiftPMImport.com.revenuecat.purchases.models.RCPromotional
import swiftPMImport.com.revenuecat.purchases.models.RCPurchaseOwnershipTypeFamilyShared
import swiftPMImport.com.revenuecat.purchases.models.RCPurchaseOwnershipTypePurchased
import swiftPMImport.com.revenuecat.purchases.models.RCPurchaseOwnershipTypeUnknown
import swiftPMImport.com.revenuecat.purchases.models.RCStripe
import swiftPMImport.com.revenuecat.purchases.models.RCTestStore
import swiftPMImport.com.revenuecat.purchases.models.RCTrial
import swiftPMImport.com.revenuecat.purchases.models.RCUnknownStore
import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.EntitlementInfo
import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kmp.models.PeriodType
import com.revenuecat.purchases.kmp.models.Store
import swiftPMImport.com.revenuecat.purchases.models.RCPeriodType as IosPeriodType
import swiftPMImport.com.revenuecat.purchases.models.RCPurchaseOwnershipType as IosOwnershipType
import swiftPMImport.com.revenuecat.purchases.models.RCStore as IosStore

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
        RCPaddle -> Store.PADDLE
        RCTestStore -> Store.TEST_STORE
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
