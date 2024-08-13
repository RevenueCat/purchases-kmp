package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedByMyApp
import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedByRevenueCat
import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedBy as IosPurchasesAreCompletedBy

internal fun IosPurchasesAreCompletedBy.toPurchasesAreCompletedBy(): PurchasesAreCompletedBy =
    when (this) {
        RCPurchasesAreCompletedByRevenueCat -> PurchasesAreCompletedBy.REVENUECAT
        RCPurchasesAreCompletedByMyApp -> PurchasesAreCompletedBy.MY_APP
        else -> error("Unexpected IosPurchasesAreCompletedBy: $this")
    }

internal fun PurchasesAreCompletedBy.toIosPurchasesAreCompletedBy(): IosPurchasesAreCompletedBy =
    when (this) {
        PurchasesAreCompletedBy.REVENUECAT -> RCPurchasesAreCompletedByRevenueCat
        PurchasesAreCompletedBy.MY_APP -> RCPurchasesAreCompletedByMyApp
    }
