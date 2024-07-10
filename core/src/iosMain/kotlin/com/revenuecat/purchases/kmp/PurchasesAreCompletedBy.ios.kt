package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedBy
import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedByMyApp
import cocoapods.PurchasesHybridCommon.RCPurchasesAreCompletedByRevenueCat

public actual enum class PurchasesAreCompletedBy {
    REVENUECAT,
    MY_APP,
}

internal fun PurchasesAreCompletedBy.toRCPurchasesAreCompletedBy(): RCPurchasesAreCompletedBy =
    when (this) {
        PurchasesAreCompletedBy.REVENUECAT -> RCPurchasesAreCompletedByRevenueCat
        PurchasesAreCompletedBy.MY_APP -> RCPurchasesAreCompletedByMyApp
    }
