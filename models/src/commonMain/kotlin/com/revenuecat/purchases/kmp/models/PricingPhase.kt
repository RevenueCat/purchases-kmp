package com.revenuecat.purchases.kmp.models

/**
 * Play Store only. Encapsulates how a user pays for a subscription at a given point in time.
 * @param billingPeriod Billing period for which the [PricingPhase] applies.
 * @param recurrenceMode [RecurrenceMode] of the [PricingPhase]
 * @param billingCycleCount Number of cycles for which the pricing phase applies. Null for
 * INFINITE_RECURRING or NON_RECURRING recurrence modes.
 * @param price [Price] of the [PricingPhase]
 * @param offerPaymentMode Indicates how the pricing phase is charged for FINITE_RECURRING
 * pricing phases
 */
public class PricingPhase internal constructor(
    public val billingPeriod: Period,
    public val recurrenceMode: RecurrenceMode,
    public val billingCycleCount: Int?,
    public val price: Price,
    public val offerPaymentMode: OfferPaymentMode?,
)
