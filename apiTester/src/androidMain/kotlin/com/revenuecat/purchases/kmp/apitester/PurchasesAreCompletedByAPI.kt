package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy

@Suppress("unused")
private class PurchasesAreCompletedByAPI {
    fun check(mode: PurchasesAreCompletedBy) {
        when (mode) {
            PurchasesAreCompletedBy.REVENUECAT,
            PurchasesAreCompletedBy.MY_APP,
            -> {
            }
        }.exhaustive
    }
}
