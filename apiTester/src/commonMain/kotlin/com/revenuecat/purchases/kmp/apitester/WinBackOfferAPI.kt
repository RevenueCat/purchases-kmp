package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.WinBackOffer

@Suppress("unused", "UNUSED_VARIABLE")
private class WinBackOfferAPI {
    fun check(winBackOffer: WinBackOffer) {
        with(winBackOffer) {
            val discount: StoreProductDiscount = discount
        }
    }
}
