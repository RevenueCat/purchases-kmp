package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PricingPhase
import com.revenuecat.purchases.models.PricingPhase as AndroidPricingPhase

internal fun AndroidPricingPhase.toPricingPhase(): PricingPhase {
    return PricingPhase(
        billingPeriod = billingPeriod.toPeriod(),
        recurrenceMode = recurrenceMode.toRecurrenceMode(),
        billingCycleCount = billingCycleCount,
        price = price.toPrice(),
        offerPaymentMode = offerPaymentMode?.toOfferPaymentMode()
    )
}
