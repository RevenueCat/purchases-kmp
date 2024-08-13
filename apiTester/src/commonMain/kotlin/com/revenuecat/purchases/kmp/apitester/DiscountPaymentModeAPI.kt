package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.DiscountPaymentMode

@Suppress("unused")
private class DiscountPaymentModeAPI {

    fun check(mode: DiscountPaymentMode) {
        when (mode) {
            DiscountPaymentMode.FREE_TRIAL,
            DiscountPaymentMode.PAY_AS_YOU_GO,
            DiscountPaymentMode.PAY_UP_FRONT,
            -> {
            }
        }.exhaustive
    }
}
