package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.StoreMessageType

@Suppress("unused")
private class StoreMessageTypeAPI {
    fun check(type: StoreMessageType) {
        when (type) {
            StoreMessageType.BILLING_ISSUES,
            StoreMessageType.GENERIC,
            StoreMessageType.PRICE_INCREASE_CONSENT -> {
            }
        }.exhaustive
    }
}
