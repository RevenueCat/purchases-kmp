package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PurchasesAreCompletedBy
import com.revenuecat.purchases.kmp.models.StoreKitVersion
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPurchasesAreCompletedBy
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPurchasesAreCompletedByMyApp
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPurchasesAreCompletedByRevenueCat
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreKitVersion
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreKitVersion1
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreKitVersion2


public fun PurchasesAreCompletedBy.toIosPurchasesAreCompletedBy(): IosPurchasesAreCompletedBy =
    IosPurchasesAreCompletedBy(
        purchasesAreCompletedBy = when (this) {
            is PurchasesAreCompletedBy.RevenueCat -> RCPurchasesAreCompletedByRevenueCat
            is PurchasesAreCompletedBy.MyApp -> RCPurchasesAreCompletedByMyApp
        },
        storeKitVersion = when (this) {
            is PurchasesAreCompletedBy.RevenueCat -> RCStoreKitVersion2
            is PurchasesAreCompletedBy.MyApp -> storeKitVersion.toIosStoreKitVersion()
        }
    )

public data class IosPurchasesAreCompletedBy(
    val purchasesAreCompletedBy: RCPurchasesAreCompletedBy,
    val storeKitVersion: RCStoreKitVersion
)
