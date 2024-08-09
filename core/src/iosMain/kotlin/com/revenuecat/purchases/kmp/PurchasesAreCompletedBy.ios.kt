package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCPurchases
import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedByMyApp
import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedByRevenueCat
import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedBy as IosPurchasesAreCompletedBy

internal fun IosPurchasesAreCompletedBy.toPurchasesAreCompletedBy(
    iOSPurchases: RCPurchases
): PurchasesAreCompletedBy =
    when (this) {
        RCPurchasesAreCompletedByRevenueCat -> PurchasesAreCompletedBy.RevenueCat
        RCPurchasesAreCompletedByMyApp -> PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)   // TODO: Inject SKVersion
        else -> error("Unexpected IosPurchasesAreCompletedBy: $this")
    }

internal fun PurchasesAreCompletedBy.toIosPurchasesAreCompletedBy(): IosPurchasesAreCompletedBy =
    when (this) {
        is PurchasesAreCompletedBy.RevenueCat -> RCPurchasesAreCompletedByRevenueCat
        is PurchasesAreCompletedBy.MyApp -> RCPurchasesAreCompletedByMyApp
    }

internal fun PurchasesAreCompletedBy.toHybridString(): String =
    when(this) {
        is PurchasesAreCompletedBy.RevenueCat -> "REVENUECAT"
        is PurchasesAreCompletedBy.MyApp -> "MY_APP"
    }
