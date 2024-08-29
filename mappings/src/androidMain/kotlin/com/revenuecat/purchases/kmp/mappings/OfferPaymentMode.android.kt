package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.OfferPaymentMode
import com.revenuecat.purchases.models.OfferPaymentMode as AndroidOfferPaymentMode

internal fun AndroidOfferPaymentMode.toOfferPaymentMode(): OfferPaymentMode {
    return when(this) {
        AndroidOfferPaymentMode.FREE_TRIAL -> OfferPaymentMode.FREE_TRIAL
        AndroidOfferPaymentMode.SINGLE_PAYMENT -> OfferPaymentMode.SINGLE_PAYMENT
        AndroidOfferPaymentMode.DISCOUNTED_RECURRING_PAYMENT ->
            OfferPaymentMode.DISCOUNTED_RECURRING_PAYMENT
    }
}
