package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.PricingPhase as AndroidPricingPhase

public actual class PricingPhase(
    internal val wrapped: AndroidPricingPhase
) {
    public actual val billingPeriod: Period = Period(wrapped.billingPeriod)
    public actual val recurrenceMode: RecurrenceMode = wrapped.recurrenceMode.toRecurrenceMode()
    public actual val billingCycleCount: Int? = wrapped.billingCycleCount
    public actual val price: Price = Price(wrapped.price)
    public actual val offerPaymentMode: OfferPaymentMode? = wrapped.offerPaymentMode?.toOfferPaymentMode()

}
