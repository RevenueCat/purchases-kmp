package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PurchasesAreCompletedBy
import com.revenuecat.purchases.PurchasesAreCompletedBy as AndroidPurchasesAreCompletedBy

public fun PurchasesAreCompletedBy.toAndroidPurchasesAreCompletedBy(): AndroidPurchasesAreCompletedBy =
    when (this) {
        is PurchasesAreCompletedBy.RevenueCat -> AndroidPurchasesAreCompletedBy.REVENUECAT
        is PurchasesAreCompletedBy.MyApp -> AndroidPurchasesAreCompletedBy.MY_APP
    }
