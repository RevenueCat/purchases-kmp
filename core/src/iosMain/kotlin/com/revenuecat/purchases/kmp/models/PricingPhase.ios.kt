package com.revenuecat.purchases.kmp.models

public actual data class PricingPhase (
    public actual val billingPeriod: Period,
    public actual val recurrenceMode: RecurrenceMode,
    public actual val billingCycleCount: Int?,
    public actual val price: Price,
    public actual val offerPaymentMode: OfferPaymentMode?,
)
