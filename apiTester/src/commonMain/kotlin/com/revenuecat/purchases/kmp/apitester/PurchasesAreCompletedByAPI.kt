package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.PurchasesAreCompletedBy
import com.revenuecat.purchases.kmp.models.StoreKitVersion

@Suppress("unused", "UNUSED_VARIABLE")
private class PurchasesAreCompletedByAPI {
    fun check(mode: PurchasesAreCompletedBy) {
        when (mode) {
            is PurchasesAreCompletedBy.RevenueCat -> { }
            is PurchasesAreCompletedBy.MyApp -> {
                val storeKitVersion: StoreKitVersion = mode.storeKitVersion
            }
        }.exhaustive
    }

    fun checkStoreKitValueInPurchasesAreCompletedByMyApp() {
        PurchasesAreCompletedBy.MyApp(storeKitVersion = StoreKitVersion.STOREKIT_2)
    }
}
