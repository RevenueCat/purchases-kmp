package com.revenuecat.purchases.kmp.models

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
    public val price: SubscriptionPrice?,

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
)

/**
 * Represents the price paid for a subscription.
 */
public class SubscriptionPrice(
    /**
     * The currency code (e.g., "USD", "EUR").
     */
    public val currency: String,
    
    /**
     * The price amount as a decimal value.
     */
    public val amount: Double,
    
    /**
     * Formatted price string (e.g., "$3.99", "€2.49").
     */
    public val formatted: String,
)