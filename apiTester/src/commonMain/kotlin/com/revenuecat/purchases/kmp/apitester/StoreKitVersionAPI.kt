package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.StoreKitVersion

@Suppress("unused")
private class StoreKitVersionAPI {
    fun check(skVersion: StoreKitVersion) {
        when (skVersion) {
            StoreKitVersion.STOREKIT_1,
            StoreKitVersion.STOREKIT_2,
            StoreKitVersion.DEFAULT,
            -> {
            }
        }.exhaustive
    }
}
