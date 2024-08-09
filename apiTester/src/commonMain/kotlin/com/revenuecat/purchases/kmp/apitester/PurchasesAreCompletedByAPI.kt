package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy

@Suppress("unused")
private class PurchasesAreCompletedByAPI {
    fun check(mode: PurchasesAreCompletedBy) {
        when (mode) {
            is PurchasesAreCompletedBy.RevenueCat,
            is PurchasesAreCompletedBy.MyApp,
            -> {
            }
        }.exhaustive
    }
}
