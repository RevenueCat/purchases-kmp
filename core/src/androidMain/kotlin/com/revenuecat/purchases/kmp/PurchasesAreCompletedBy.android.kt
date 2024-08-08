package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.PurchasesAreCompletedBy as AndroidPurchasesAreCompletedBy

internal fun AndroidPurchasesAreCompletedBy.toPurchasesAreCompletedBy(): PurchasesAreCompletedBy =
    when (this) {
        AndroidPurchasesAreCompletedBy.REVENUECAT -> PurchasesAreCompletedBy.RevenueCat
        AndroidPurchasesAreCompletedBy.MY_APP -> PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT) // TODO: Inject SKVersion
    }

internal fun PurchasesAreCompletedBy.toAndroidPurchasesAreCompletedBy(): AndroidPurchasesAreCompletedBy =
    when (this) {
        is PurchasesAreCompletedBy.RevenueCat -> AndroidPurchasesAreCompletedBy.REVENUECAT
        is PurchasesAreCompletedBy.MyApp -> AndroidPurchasesAreCompletedBy.MY_APP
    }

internal fun PurchasesAreCompletedBy.toHybridString(): String =
    when(this) {
        is PurchasesAreCompletedBy.RevenueCat -> "REVENUECAT"
        is PurchasesAreCompletedBy.MyApp -> "MY_APP"
    }