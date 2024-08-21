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
public data class PricingPhase(
    val billingPeriod: Period,
    val recurrenceMode: RecurrenceMode,
    val billingCycleCount: Int?,
    val price: Price,
    val offerPaymentMode: OfferPaymentMode?,
)
