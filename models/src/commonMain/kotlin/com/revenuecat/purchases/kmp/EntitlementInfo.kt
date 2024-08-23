package com.revenuecat.purchases.kmp

/**
 * This object gives you access to all of the information about the status of a user's entitlements.
 */
public data class EntitlementInfo(
    /**
     * The entitlement identifier configured in the RevenueCat dashboard.
     */
    public val identifier: String,

    /**
     * True if the user has access to this entitlement.
     */
    public val isActive: Boolean,

    /**
     * True if the underlying subscription is set to renew at the end of the billing period
     * (expirationDate). Will always be True if entitlement is for lifetime access.
     */
    public val willRenew: Boolean,

    /**
     * The last period type this entitlement was in.
     */
    public val periodType: PeriodType,

    /**
     * Nullable on iOS only not on Android. The latest purchase or renewal date for the entitlement,
     * in millis since the Unix epoch.
     */
    public val latestPurchaseDateMillis: Long?,

    /**
     * Nullable on iOS only not on Android. The first date this entitlement was purchased in millis
     * since the Unix epoch.
     */
    public val originalPurchaseDateMillis: Long?,

    /**
     * The expiration date for the entitlement, can be `null` for lifetime access. If the [periodType]
     * is [PeriodType.TRIAL], this is the trial expiration date.
     */
    public val expirationDateMillis: Long?,

    /**
     * The store where this entitlement was unlocked from.
     */
    public val store: Store,

    /**
     * The product identifier that unlocked this entitlement.
     *  * For Google subscriptions, this is the subscription ID.
     *  * For Amazon subscriptions, this is the `termSku`.
     *  * For INAPP purchases, this is simply the `productId`.
     */
    public val productIdentifier: String,

    /**
     * Play Store only. The base plan identifier that unlocked this entitlement.
     */
    public val productPlanIdentifier: String?,

    /**
     * False if this entitlement is unlocked via a production purchase.
     */
    public val isSandbox: Boolean,

    /**
     * The date an unsubscribe was detected. Can be `null`.
     *
     * Note: Entitlement may still be active even if user has unsubscribed. Check the [isActive]
     * property.
     */
    public val unsubscribeDetectedAtMillis: Long?,

    /**
     * The date a billing issue was detected in millis since the Unix epoch. Can be `null` if there is
     * no billing issue or an issue has been resolved. Note: Entitlement may still be active even if
     * there is a billing issue. Check the [isActive] property.
     */
    public val billingIssueDetectedAtMillis: Long?,
    public val ownershipType: OwnershipType,

    /**
     * If entitlement verification was enabled, the result of that verification. If not,
     * [VerificationResult.NOT_REQUESTED].
     */
    public val verification: VerificationResult
)

/**
 * Enum of supported stores
 */
public enum class Store {
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
public enum class PeriodType {
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
public enum class OwnershipType {
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
