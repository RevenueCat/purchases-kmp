package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCAmazon
import cocoapods.PurchasesHybridCommon.RCAppStore
import cocoapods.PurchasesHybridCommon.RCEntitlementInfo
import cocoapods.PurchasesHybridCommon.RCIntro
import cocoapods.PurchasesHybridCommon.RCMacAppStore
import cocoapods.PurchasesHybridCommon.RCNormal
import cocoapods.PurchasesHybridCommon.RCPlayStore
import cocoapods.PurchasesHybridCommon.RCPromotional
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypeFamilyShared
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypePurchased
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypeUnknown
import cocoapods.PurchasesHybridCommon.RCStripe
import cocoapods.PurchasesHybridCommon.RCTrial
import cocoapods.PurchasesHybridCommon.RCUnknownStore
import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.EntitlementInfo
import com.revenuecat.purchases.kmp.OwnershipType
import com.revenuecat.purchases.kmp.PeriodType
import com.revenuecat.purchases.kmp.Store
import cocoapods.PurchasesHybridCommon.RCPeriodType as IosPeriodType
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipType as IosOwnershipType
import cocoapods.PurchasesHybridCommon.RCStore as IosStore

internal fun RCEntitlementInfo.toEntitlementInfo(): EntitlementInfo {
    return EntitlementInfo(
        identifier(),
        isActive(),
        willRenew(),
        periodType().toPeriodType(),
        latestPurchaseDate()?.toEpochMilliseconds(),
        originalPurchaseDate()?.toEpochMilliseconds(),
        expirationDate()?.toEpochMilliseconds(),
        store().toStore(),
        productIdentifier(),
        productPlanIdentifier(),
        isSandbox(),
        unsubscribeDetectedAt()?.toEpochMilliseconds(),
        billingIssueDetectedAt()?.toEpochMilliseconds(),
        ownershipType().toOwnershipType(),
        verification().toVerificationResult()
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
        else -> error("Unknown IosStore: $this")
    }

internal fun IosPeriodType.toPeriodType(): PeriodType =
    when (this) {
        RCNormal -> PeriodType.NORMAL
        RCIntro -> PeriodType.INTRO
        RCTrial -> PeriodType.TRIAL
        else -> error("Unknown IosPeriodType: $this")
    }

internal fun IosOwnershipType.toOwnershipType(): OwnershipType =
    when (this) {
        RCPurchaseOwnershipTypePurchased -> OwnershipType.PURCHASED
        RCPurchaseOwnershipTypeFamilyShared -> OwnershipType.FAMILY_SHARED
        RCPurchaseOwnershipTypeUnknown -> OwnershipType.UNKNOWN
        else -> error("Unknown IosOwnershipType: $this")
    }
