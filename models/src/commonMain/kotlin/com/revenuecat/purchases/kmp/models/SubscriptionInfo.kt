package com.revenuecat.purchases.kmp.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Subscription purchases of the Customer.
 */
public class SubscriptionInfo(
    /**
     * The product identifier.
     */
    public val productIdentifier: String,

    /**
     * Date when the last subscription period started, in milliseconds since the Unix epoch.
     */
    public val purchaseDateMillis: Long,

    /**
     * Date when this subscription first started, in milliseconds since the Unix epoch.
     * This property does not update with renewals.
     * This property also does not update for product changes within a subscription group or
     * re-subscriptions by lapsed subscribers.
     */
    public val originalPurchaseDateMillis: Long?,

    /**
     * Date when the subscription expires/expired, in milliseconds since the Unix epoch.
     */
    public val expiresDateMillis: Long?,

    /**
     * Store where the subscription was purchased.
     */
    public val store: Store,

    /**
     * Whether the purchase was made in sandbox mode.
     */
    public val isSandbox: Boolean,

    /**
     * Date when RevenueCat detected that auto-renewal was turned off for this subscription,
     * in milliseconds since the Unix epoch.
     * Note the subscription may still be active, check the [expiresDateMillis] attribute.
     */
    public val unsubscribeDetectedAtMillis: Long?,

    /**
     * Date when RevenueCat detected any billing issues with this subscription,
     * in milliseconds since the Unix epoch.
     * If and when the billing issue gets resolved, this field is set to null.
     * Note the subscription may still be active, check the [expiresDateMillis] attribute.
     */
    public val billingIssuesDetectedAtMillis: Long?,

    /**
     * Date when any grace period for this subscription expires/expired,
     * in milliseconds since the Unix epoch.
     * null if the customer has never been in a grace period.
     */
    public val gracePeriodExpiresDateMillis: Long?,

    /**
     * How the Customer received access to this subscription:
     * - [OwnershipType.PURCHASED]: The customer bought the subscription.
     * - [OwnershipType.FAMILY_SHARED]: The Customer has access to the product via their family.
     */
    public val ownershipType: OwnershipType,

    /**
     * Type of the current subscription period:
     * - [PeriodType.NORMAL]: The product is in a normal period (default)
     * - [PeriodType.TRIAL]: The product is in a free trial period
     * - [PeriodType.INTRO]: The product is in an introductory pricing period
     * - [PeriodType.PREPAID]: The product is in a prepaid pricing period
     */
    public val periodType: PeriodType,

    /**
     * Date when RevenueCat detected a refund of this subscription,
     * in milliseconds since the Unix epoch.
     */
    public val refundedAtMillis: Long?,

    /**
     * The transaction id in the store of the subscription.
     */
    public val storeTransactionId: String?,

    /**
     * Date when the subscription will auto-resume, in milliseconds since the Unix epoch.
     * This property is only applicable for Google Play subscriptions
     * and will only have a value when the subscription is currently paused.
     */
    public val autoResumeDateMillis: Long?,


    /**
     * Paid price for the subscription.
     * Contains currency and amount information.
     */
    public val price: Price?,

    /**
     * The identifier of the product plan.
     */
    public val productPlanIdentifier: String?,

    /**
     * URL to manage this subscription.
     */
    public val managementUrlString: String?,

    /**
     * Whether the subscription is currently active.
     */
    public val isActive: Boolean,

    /**
     * Whether the subscription will renew at the next billing period.
     */
    public val willRenew: Boolean,
) {

    /**
     * Date when the last subscription period started.
     */
    @ExperimentalTime
    public val purchaseDate: Instant by lazy {
        Instant.fromEpochMilliseconds(purchaseDateMillis)
    }

    /**
     * Date when this subscription first started.
     * This property does not update with renewals.
     * This property also does not update for product changes within a subscription group or
     * re-subscriptions by lapsed subscribers.
     */
    @ExperimentalTime
    public val originalPurchaseDate: Instant? by lazy {
        originalPurchaseDateMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * Date when the subscription expires/expired.
     */
    @ExperimentalTime
    public val expiresDate: Instant? by lazy {
        expiresDateMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * Date when RevenueCat detected that auto-renewal was turned off for this subscription.
     * Note the subscription may still be active, check the [expiresDate] attribute.
     */
    @ExperimentalTime
    public val unsubscribeDetectedAt: Instant? by lazy {
        unsubscribeDetectedAtMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * Date when RevenueCat detected any billing issues with this subscription.
     * If and when the billing issue gets resolved, this field is set to null.
     * Note the subscription may still be active, check the [expiresDate] attribute.
     */
    @ExperimentalTime
    public val billingIssuesDetectedAt: Instant? by lazy {
        billingIssuesDetectedAtMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * Date when any grace period for this subscription expires/expired.
     * null if the customer has never been in a grace period.
     */
    @ExperimentalTime
    public val gracePeriodExpiresDate: Instant? by lazy {
        gracePeriodExpiresDateMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * Date when RevenueCat detected a refund of this subscription.
     */
    @ExperimentalTime
    public val refundedAt: Instant? by lazy {
        refundedAtMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * Date when the subscription will auto-resume.
     * This property is only applicable for Google Play subscriptions
     * and will only have a value when the subscription is currently paused.
     */
    @ExperimentalTime
    public val autoResumeDate: Instant? by lazy {
        autoResumeDateMillis?.let { Instant.fromEpochMilliseconds(it) }
    }

}
