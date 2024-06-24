package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext

/**
 * Play Store only. A purchase-able entity for a subscription product.
 */
public expect interface SubscriptionOption {
    /**
     * For Google subscriptions:
     * If this SubscriptionOption represents a base plan, this will be the basePlanId.
     * If it represents an offer, it will be {basePlanId}:{offerId}
     *
     * Not applicable for Amazon subscriptions.
     */
    public val id: String

    /**
     * Pricing phases defining a user's payment plan for the product over time.
     */
    public val pricingPhases: List<PricingPhase>

    /**
     * Tags defined on the base plan or offer. Keep in mind that offers automatically
     * inherit their base plan's tag. Empty for Amazon.
     */
    public val tags: List<String>

    /**
     * The offering ID this `SubscriptionOption` was returned from.
     *
     * Null if not using RevenueCat offerings system, or if fetched directly via `Purchases.getProducts`
     */
    @Deprecated(
        "Use presentedOfferingContext instead",
        ReplaceWith("presentedOfferingContext.offeringIdentifier"),
    )
    public val presentedOfferingIdentifier: String?

    /**
     * The context from which this subscription option was obtained.
     *
     * Null if not using RevenueCat offerings system, if fetched directly via
     * `Purchases.getProducts`, or on restores/syncs.
     */
    public val presentedOfferingContext: PresentedOfferingContext?

    public val purchasingData: PurchasingData
}

/**
 * True if this SubscriptionOption represents a Google subscription base plan (rather than an offer).
 * Not applicable for Amazon subscriptions.
 */
public val SubscriptionOption.isBasePlan: Boolean
    get() = pricingPhases.size == 1

/**
 * The subscription period of `fullPricePhase` (after free and intro trials).
 */
public val SubscriptionOption.billingPeriod: Period?
    get() = fullPricePhase?.billingPeriod

/**
 * True if the subscription is pre-paid.
 * Not applicable for Amazon subscriptions.
 */
public val SubscriptionOption.isPrepaid: Boolean
    get() = fullPricePhase?.recurrenceMode == RecurrenceMode.NON_RECURRING

/**
 * The full price [PricingPhase] of the subscription.
 * Looks for the last price phase of the SubscriptionOption.
 */
public val SubscriptionOption.fullPricePhase: PricingPhase?
    get() = pricingPhases.lastOrNull()

/**
 * The free trial [PricingPhase] of the subscription.
 * Looks for the first pricing phase of the SubscriptionOption where `amountMicros` is 0.
 * There can be a `freeTrialPhase` and an `introductoryPhase` in the same [SubscriptionOption].
 */
public val SubscriptionOption.freePhase: PricingPhase?
    get() = pricingPhases.dropLast(1).firstOrNull {
        it.price.amountMicros == 0L
    }

/**
 * The intro trial [PricingPhase] of the subscription.
 * Looks for the first pricing phase of the SubscriptionOption where `amountMicros` is greater than 0.
 * There can be a `freeTrialPhase` and an `introductoryPhase` in the same [SubscriptionOption].
 */
public val SubscriptionOption.introPhase: PricingPhase?
    get() = pricingPhases.dropLast(1).firstOrNull {
        it.price.amountMicros > 0L
    }
