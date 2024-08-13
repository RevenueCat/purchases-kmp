package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.PurchasesAreCompletedBy as AndroidPurchasesAreCompletedBy

internal fun AndroidPurchasesAreCompletedBy.toPurchasesAreCompletedBy(): PurchasesAreCompletedBy =
    when (this) {
        AndroidPurchasesAreCompletedBy.REVENUECAT -> PurchasesAreCompletedBy.REVENUECAT
        AndroidPurchasesAreCompletedBy.MY_APP -> PurchasesAreCompletedBy.MY_APP
    }

internal fun PurchasesAreCompletedBy.toAndroidPurchasesAreCompletedBy(): AndroidPurchasesAreCompletedBy =
    when (this) {
        PurchasesAreCompletedBy.REVENUECAT -> AndroidPurchasesAreCompletedBy.REVENUECAT
        PurchasesAreCompletedBy.MY_APP -> AndroidPurchasesAreCompletedBy.MY_APP
    }
