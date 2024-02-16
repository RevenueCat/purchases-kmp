package io.shortway.kobankat

public expect class EntitlementInfo

public expect val EntitlementInfo.identifier: String
public expect val EntitlementInfo.isActive: Boolean
public expect val EntitlementInfo.willRenew: Boolean
public expect val EntitlementInfo.periodType: PeriodType
/**
 * Nullable on iOS only not on Android.
 */
public expect val EntitlementInfo.latestPurchaseDateMillis: Long?
/**
 * Nullable on iOS only not on Android.
 */
public expect val EntitlementInfo.originalPurchaseDateMillis: Long?
public expect val EntitlementInfo.expirationDateMillis: Long?
public expect val EntitlementInfo.store: Store
public expect val EntitlementInfo.productIdentifier: String
public expect val EntitlementInfo.productPlanIdentifier: String?
public expect val EntitlementInfo.isSandbox: Boolean
public expect val EntitlementInfo.unsubscribeDetectedAtMillis: Long?
public expect val EntitlementInfo.billingIssueDetectedAtMillis: Long?
public expect val EntitlementInfo.ownershipType: OwnershipType
public expect val EntitlementInfo.verification: VerificationResult

/**
 * Enum of supported stores
 */
public expect enum class Store {
    /**
     * For entitlements granted via Apple App Store.
     */
    APP_STORE,

    /**
     * For entitlements granted via Apple Mac App Store.
     */
    MAC_APP_STORE,

    /**
     * For entitlements granted via Google Play Store.
     */
    PLAY_STORE,

    /**
     * For entitlements granted via Stripe.
     */
    STRIPE,

    /**
     * For entitlements granted via a promo in RevenueCat.
     */
    PROMOTIONAL,

    /**
     * For entitlements granted via an unknown store.
     */
    UNKNOWN_STORE,

    /**
     * For entitlements granted via Amazon store.
     */
    AMAZON,
}

/**
 * Enum of supported period types for an entitlement.
 */
public expect enum class PeriodType {
    /**
     * If the entitlement is not under an introductory or trial period.
     */
    NORMAL,

    /**
     * If the entitlement is under a introductory price period.
     */
    INTRO,

    /**
     * If the entitlement is under a trial period.
     */
    TRIAL,
}

/**
 * Enum of supported ownership types for an entitlement.
 */
public expect enum class OwnershipType {
    /**
     * The purchase was made directly by this user.
     */
    PURCHASED,

    /**
     * The purchase has been shared to this user by a family member.
     */
    FAMILY_SHARED,

    /**
     * The purchase has no or an unknown ownership type.
     */
    UNKNOWN,
}