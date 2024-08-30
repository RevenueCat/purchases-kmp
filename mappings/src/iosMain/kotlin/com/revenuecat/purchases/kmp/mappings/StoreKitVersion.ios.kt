package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.StoreKitVersion

public fun StoreKitVersion.toHybridString(): String =
    when(this) {
        StoreKitVersion.STOREKIT_1 -> "STOREKIT_1"
        StoreKitVersion.STOREKIT_2 -> "STOREKIT_2"
        StoreKitVersion.DEFAULT -> "DEFAULT"
    }
