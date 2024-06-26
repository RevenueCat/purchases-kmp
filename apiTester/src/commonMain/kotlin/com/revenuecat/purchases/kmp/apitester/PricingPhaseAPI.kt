package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.OfferPaymentMode
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.models.PricingPhase
import com.revenuecat.purchases.kmp.models.RecurrenceMode

@Suppress("unused", "UNUSED_VARIABLE")
private class PricingPhaseAPI {
    fun checkPricingPhase(pricingPhase: PricingPhase) {
        val billingPeriod: Period = pricingPhase.billingPeriod
        val recurrenceMode: RecurrenceMode = pricingPhase.recurrenceMode
        val billingCycleCount: Int? = pricingPhase.billingCycleCount
        val price: Price = pricingPhase.price
        val offerPaymentMode: OfferPaymentMode? = pricingPhase.offerPaymentMode
    }

    fun checkingPrice(price: Price) {
        val formatted: String = price.formatted
        val amountMicros: Long = price.amountMicros
        val currencyCode: String = price.currencyCode
    }
}
