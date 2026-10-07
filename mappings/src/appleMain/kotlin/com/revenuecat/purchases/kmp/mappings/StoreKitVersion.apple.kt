package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.StoreKitVersion
import com.revenuecat.purchases.kn.core.RCStoreKitVersion
import com.revenuecat.purchases.kn.core.RCStoreKitVersion1
import com.revenuecat.purchases.kn.core.RCStoreKitVersion2

public fun StoreKitVersion.toIosStoreKitVersion(): RCStoreKitVersion =
    when (this) {
        StoreKitVersion.STOREKIT_1 -> RCStoreKitVersion1
        StoreKitVersion.STOREKIT_2 -> RCStoreKitVersion2
        StoreKitVersion.DEFAULT -> RCStoreKitVersion2
    }
