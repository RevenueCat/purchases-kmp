package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy
import com.revenuecat.purchases.kmp.StoreKitVersion

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

    fun checkStoreKitValueInPurchasesAreCompletedByMyApp() {
        PurchasesAreCompletedBy.MyApp(storeKitVersion = StoreKitVersion.STOREKIT_2)
    }
}
