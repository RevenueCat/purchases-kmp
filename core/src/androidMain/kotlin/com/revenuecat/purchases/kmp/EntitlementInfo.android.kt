@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.EntitlementInfo as RcEntitlementInfo
import com.revenuecat.purchases.OwnershipType as AndroidOwnershipType
import com.revenuecat.purchases.PeriodType as AndroidPeriodType
import com.revenuecat.purchases.Store as AndroidStore

public actual typealias EntitlementInfo = RcEntitlementInfo

public actual val EntitlementInfo.identifier: String
    get() = identifier
public actual val EntitlementInfo.isActive: Boolean
    get() = isActive
public actual val EntitlementInfo.willRenew: Boolean
    get() = willRenew
public actual val EntitlementInfo.periodType: PeriodType
    get() = periodType.toPeriodType()
public actual val EntitlementInfo.latestPurchaseDateMillis: Long?
    get() = latestPurchaseDate.time
public actual val EntitlementInfo.originalPurchaseDateMillis: Long?
    get() = originalPurchaseDate.time
public actual val EntitlementInfo.expirationDateMillis: Long?
    get() = expirationDate?.time
public actual val EntitlementInfo.store: Store
    get() = store.toStore()
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
    get() = ownershipType.toOwnershipType()
public actual val EntitlementInfo.verification: VerificationResult
    get() = verification.toVerificationResult()

internal fun AndroidStore.toStore(): Store =
    when (this) {
        AndroidStore.APP_STORE -> Store.APP_STORE
        AndroidStore.MAC_APP_STORE -> Store.MAC_APP_STORE
        AndroidStore.PLAY_STORE -> Store.PLAY_STORE
        AndroidStore.STRIPE -> Store.STRIPE
        AndroidStore.PROMOTIONAL -> Store.PROMOTIONAL
        AndroidStore.UNKNOWN_STORE -> Store.UNKNOWN_STORE
        AndroidStore.AMAZON -> Store.AMAZON
        AndroidStore.RC_BILLING -> Store.RC_BILLING
        AndroidStore.EXTERNAL -> Store.EXTERNAL
    }

internal fun Store.toAndroidStore(): AndroidStore =
    when (this) {
        Store.APP_STORE -> AndroidStore.APP_STORE
        Store.MAC_APP_STORE -> AndroidStore.MAC_APP_STORE
        Store.PLAY_STORE -> AndroidStore.PLAY_STORE
        Store.STRIPE -> AndroidStore.STRIPE
        Store.PROMOTIONAL -> AndroidStore.PROMOTIONAL
        Store.UNKNOWN_STORE -> AndroidStore.UNKNOWN_STORE
        Store.AMAZON -> AndroidStore.AMAZON
        Store.RC_BILLING -> AndroidStore.RC_BILLING
        Store.EXTERNAL -> AndroidStore.EXTERNAL
    }

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
