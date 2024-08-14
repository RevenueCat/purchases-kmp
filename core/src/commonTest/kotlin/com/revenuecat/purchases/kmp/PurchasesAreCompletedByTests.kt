package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals

class PurchasesAreCompletedByTests {

    @Test
    fun testToHybridString() {
        val purchasesAreCompletedByMyApp = PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)
        val myAppHybridString = purchasesAreCompletedByMyApp.toHybridString()

        val purchasesAreCompletedByRevenueCat = PurchasesAreCompletedBy.RevenueCat
        val revenuecatHybridString = purchasesAreCompletedByRevenueCat.toHybridString()

        assertEquals("MY_APP", myAppHybridString)
        assertEquals("REVENUECAT", revenuecatHybridString)
    }
}