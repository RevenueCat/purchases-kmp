package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.OfferPaymentMode

@Suppress("unused")
private class OfferPaymentModeAPI {
    fun check(offerPaymentMode: OfferPaymentMode) {
        when (offerPaymentMode) {
            OfferPaymentMode.DISCOUNTED_RECURRING_PAYMENT,
            OfferPaymentMode.SINGLE_PAYMENT,
            OfferPaymentMode.FREE_TRIAL,
            -> {
            }
        }.exhaustive
    }
}
