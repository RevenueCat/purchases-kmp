package io.shortway.kobankat.apitester

import io.shortway.kobankat.models.OfferPaymentMode
import io.shortway.kobankat.models.Period
import io.shortway.kobankat.models.Price
import io.shortway.kobankat.models.PricingPhase
import io.shortway.kobankat.models.RecurrenceMode

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
