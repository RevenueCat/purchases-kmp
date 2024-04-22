package io.shortway.kobankat

import cocoapods.RevenueCat.RCAmazon
import cocoapods.RevenueCat.RCAppStore
import cocoapods.RevenueCat.RCEntitlementInfo
import cocoapods.RevenueCat.RCIntro
import cocoapods.RevenueCat.RCMacAppStore
import cocoapods.RevenueCat.RCNormal
import cocoapods.RevenueCat.RCPeriodType
import cocoapods.RevenueCat.RCPlayStore
import cocoapods.RevenueCat.RCPromotional
import cocoapods.RevenueCat.RCPurchaseOwnershipType
import cocoapods.RevenueCat.RCPurchaseOwnershipTypeFamilyShared
import cocoapods.RevenueCat.RCPurchaseOwnershipTypePurchased
import cocoapods.RevenueCat.RCPurchaseOwnershipTypeUnknown
import cocoapods.RevenueCat.RCStore
import cocoapods.RevenueCat.RCStripe
import cocoapods.RevenueCat.RCTrial
import cocoapods.RevenueCat.RCUnknownStore
import io.shortway.kobankat.ktx.toEpochMilliseconds

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
