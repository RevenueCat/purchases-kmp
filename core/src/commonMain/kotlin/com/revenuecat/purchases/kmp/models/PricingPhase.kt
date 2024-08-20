package com.revenuecat.purchases.kmp.models

/**
 * Play Store only. Encapsulates how a user pays for a subscription at a given point in time.
 */
public expect class PricingPhase {

    /**
     *  Billing period for which the [PricingPhase] applies.
     */
    public val billingPeriod: Period

    /**
     * [RecurrenceMode] of the [PricingPhase]
     */
    public val recurrenceMode: RecurrenceMode

    /**
     * Number of cycles for which the pricing phase applies.
     * Null for INFINITE_RECURRING or NON_RECURRING recurrence modes.
     */
    public val billingCycleCount: Int?

    /**
     * [Price] of the [PricingPhase]
     */
    public val price: Price

    /**
     * Indicates how the pricing phase is charged for FINITE_RECURRING pricing phases
     */
    public val offerPaymentMode: OfferPaymentMode?
}
