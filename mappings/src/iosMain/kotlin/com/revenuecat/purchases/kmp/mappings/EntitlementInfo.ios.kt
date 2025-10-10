package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCAmazon
import cocoapods.PurchasesHybridCommon.RCAppStore
import cocoapods.PurchasesHybridCommon.RCBilling
import cocoapods.PurchasesHybridCommon.RCEntitlementInfo
import cocoapods.PurchasesHybridCommon.RCIntro
import cocoapods.PurchasesHybridCommon.RCMacAppStore
import cocoapods.PurchasesHybridCommon.RCNormal
import cocoapods.PurchasesHybridCommon.RCPaddle
import cocoapods.PurchasesHybridCommon.RCPrepaid
import cocoapods.PurchasesHybridCommon.RCPlayStore
import cocoapods.PurchasesHybridCommon.RCPromotional
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypeFamilyShared
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypePurchased
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypeUnknown
import cocoapods.PurchasesHybridCommon.RCStripe
import cocoapods.PurchasesHybridCommon.RCTestStore
import cocoapods.PurchasesHybridCommon.RCTrial
import cocoapods.PurchasesHybridCommon.RCUnknownStore
import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.EntitlementInfo
import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kmp.models.PeriodType
import com.revenuecat.purchases.kmp.models.Store
import cocoapods.PurchasesHybridCommon.RCPeriodType as IosPeriodType
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipType as IosOwnershipType
import cocoapods.PurchasesHybridCommon.RCStore as IosStore

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
