package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.DiscountType

@Suppress("unused")
private class DiscountTypeAPI {

    fun check(type: DiscountType) {
        when (type) {
            DiscountType.INTRODUCTORY,
            DiscountType.PROMOTIONAL,
            -> {
            }
        }.exhaustive
    }
}
