package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy
import com.revenuecat.purchases.kmp.StoreKitVersion

@Suppress("unused")
private class PurchasesAreCompletedByAPI {
    fun check() {
        PurchasesAreCompletedBy.RevenueCat
        PurchasesAreCompletedBy.MyApp(storeKitVersion = StoreKitVersion.STOREKIT_2)
    }
}
