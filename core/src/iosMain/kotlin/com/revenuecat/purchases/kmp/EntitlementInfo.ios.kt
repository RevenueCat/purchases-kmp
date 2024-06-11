package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCAmazon
import cocoapods.PurchasesHybridCommon.RCAppStore
import cocoapods.PurchasesHybridCommon.RCEntitlementInfo
import cocoapods.PurchasesHybridCommon.RCIntro
import cocoapods.PurchasesHybridCommon.RCMacAppStore
import cocoapods.PurchasesHybridCommon.RCNormal
import cocoapods.PurchasesHybridCommon.RCPeriodType
import cocoapods.PurchasesHybridCommon.RCPlayStore
import cocoapods.PurchasesHybridCommon.RCPromotional
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipType
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypeFamilyShared
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypePurchased
import cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipTypeUnknown
import cocoapods.PurchasesHybridCommon.RCStore
import cocoapods.PurchasesHybridCommon.RCStripe
import cocoapods.PurchasesHybridCommon.RCTrial
import cocoapods.PurchasesHybridCommon.RCUnknownStore
import com.revenuecat.purchases.kmp.ktx.toEpochMilliseconds

public actual typealias EntitlementInfo = RCEntitlementInfo

public actual val EntitlementInfo.identifier: String
    get() = identifier()
public actual val EntitlementInfo.isActive: Boolean
    get() = isActive()
public actual val EntitlementInfo.willRenew: Boolean
    get() = willRenew()
public actual val EntitlementInfo.periodType: PeriodType
    get() = periodType().toPeriodType()
public actual val EntitlementInfo.latestPurchaseDateMillis: Long?
    get() = latestPurchaseDate()?.toEpochMilliseconds()
public actual val EntitlementInfo.originalPurchaseDateMillis: Long?
    get() = originalPurchaseDate()?.toEpochMilliseconds()
public actual val EntitlementInfo.expirationDateMillis: Long?
    get() = expirationDate()?.toEpochMilliseconds()
public actual val EntitlementInfo.store: Store
    get() = store().toStore()
public actual val EntitlementInfo.productIdentifier: String
    get() = productIdentifier()
public actual val EntitlementInfo.productPlanIdentifier: String?
    get() = productPlanIdentifier()
public actual val EntitlementInfo.isSandbox: Boolean
    get() = isSandbox()
public actual val EntitlementInfo.unsubscribeDetectedAtMillis: Long?
    get() = unsubscribeDetectedAt()?.toEpochMilliseconds()
public actual val EntitlementInfo.billingIssueDetectedAtMillis: Long?
    get() = billingIssueDetectedAt()?.toEpochMilliseconds()
public actual val EntitlementInfo.ownershipType: OwnershipType
    get() = ownershipType().toOwnershipType()
public actual val EntitlementInfo.verification: VerificationResult
    get() = verification().toVerificationResult()

public actual enum class Store {
    APP_STORE,
    MAC_APP_STORE,
    PLAY_STORE,
    STRIPE,
    PROMOTIONAL,
    UNKNOWN_STORE,
    AMAZON,
    RC_BILLING,
    EXTERNAL,
}

public actual enum class PeriodType {
    NORMAL,
    INTRO,
    TRIAL,
}

public actual enum class OwnershipType {
    PURCHASED,
    FAMILY_SHARED,
    UNKNOWN,
}

internal fun RCStore.toStore(): Store =
    when (this) {
        RCAppStore -> Store.APP_STORE
        RCMacAppStore -> Store.MAC_APP_STORE
        RCPlayStore -> Store.PLAY_STORE
        RCStripe -> Store.STRIPE
        RCPromotional -> Store.PROMOTIONAL
        RCUnknownStore -> Store.UNKNOWN_STORE
        RCAmazon -> Store.AMAZON
        else -> error("Unknown RCStore: $this")
    }

internal fun RCPeriodType.toPeriodType(): PeriodType =
    when (this) {
        RCNormal -> PeriodType.NORMAL
        RCIntro -> PeriodType.INTRO
        RCTrial -> PeriodType.TRIAL
        else -> error("Unknown RCPeriodType: $this")
    }

internal fun RCPurchaseOwnershipType.toOwnershipType(): OwnershipType =
    when (this) {
        RCPurchaseOwnershipTypePurchased -> OwnershipType.PURCHASED
        RCPurchaseOwnershipTypeFamilyShared -> OwnershipType.FAMILY_SHARED
        RCPurchaseOwnershipTypeUnknown -> OwnershipType.UNKNOWN
        else -> error("Unknown RCPurchaseOwnershipType: $this")
    }
