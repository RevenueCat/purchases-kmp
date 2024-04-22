package io.shortway.kobankat

/**
 * This object gives you access to all of the information about the status of a user's entitlements.
 */
public expect class EntitlementInfo

/**
 * The entitlement identifier configured in the RevenueCat dashboard.
 */
public expect val EntitlementInfo.identifier: String

/**
 * True if the user has access to this entitlement.
 */
public expect val EntitlementInfo.isActive: Boolean

/**
 * True if the underlying subscription is set to renew at the end of the billing period
 * (expirationDate). Will always be True if entitlement is for lifetime access.
 */
public expect val EntitlementInfo.willRenew: Boolean

/**
 * The last period type this entitlement was in.
 */
public expect val EntitlementInfo.periodType: PeriodType

/**
 * Nullable on iOS only not on Android. The latest purchase or renewal date for the entitlement,
 * in millis since the Unix epoch.
 */
public expect val EntitlementInfo.latestPurchaseDateMillis: Long?

/**
 * Nullable on iOS only not on Android. The first date this entitlement was purchased in millis
 * since the Unix epoch.
 */
public expect val EntitlementInfo.originalPurchaseDateMillis: Long?

/**
 * The expiration date for the entitlement, can be `null` for lifetime access. If the [periodType]
 * is [PeriodType.TRIAL], this is the trial expiration date.
 */
public expect val EntitlementInfo.expirationDateMillis: Long?

/**
 * The store where this entitlement was unlocked from.
 */
public expect val EntitlementInfo.store: Store

/**
 * The product identifier that unlocked this entitlement.
 *  * For Google subscriptions, this is the subscription ID.
 *  * For Amazon subscriptions, this is the `termSku`.
 *  * For INAPP purchases, this is simply the `productId`.
 */
public expect val EntitlementInfo.productIdentifier: String

/**
 * Play Store only. The base plan identifier that unlocked this entitlement.
 */
public expect val EntitlementInfo.productPlanIdentifier: String?

/**
 * False if this entitlement is unlocked via a production purchase.
 */
public expect val EntitlementInfo.isSandbox: Boolean

/**
 * The date an unsubscribe was detected. Can be `null`.
 *
 * Note: Entitlement may still be active even if user has unsubscribed. Check the [isActive]
 * property.
 */
public expect val EntitlementInfo.unsubscribeDetectedAtMillis: Long?

/**
 * The date a billing issue was detected in millis since the Unix epoch. Can be `null` if there is
 * no billing issue or an issue has been resolved. Note: Entitlement may still be active even if
 * there is a billing issue. Check the [isActive] property.
 */
public expect val EntitlementInfo.billingIssueDetectedAtMillis: Long?
public expect val EntitlementInfo.ownershipType: OwnershipType

/**
 * If entitlement verification was enabled, the result of that verification. If not,
 * [VerificationResult.NOT_REQUESTED].
 */
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

    /**
     * For entitlements granted via RC Billing.
     */
    RC_BILLING,

    /**
     * For entitlements granted via RevenueCat's External Purchases API.
     */
    EXTERNAL,
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
