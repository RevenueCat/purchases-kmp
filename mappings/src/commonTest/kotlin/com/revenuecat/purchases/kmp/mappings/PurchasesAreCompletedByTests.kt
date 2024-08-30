package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy
import com.revenuecat.purchases.kmp.StoreKitVersion
import kotlin.test.Test
import kotlin.test.assertEquals

class PurchasesAreCompletedByTests {

    @Test
    fun `toHybridString returns the correct values`() {
        val purchasesAreCompletedByMyApp = PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)
        val myAppHybridString = purchasesAreCompletedByMyApp.toHybridString()

        val purchasesAreCompletedByRevenueCat = PurchasesAreCompletedBy.RevenueCat
        val revenuecatHybridString = purchasesAreCompletedByRevenueCat.toHybridString()

        assertEquals("MY_APP", myAppHybridString)
        assertEquals("REVENUECAT", revenuecatHybridString)
    }
}
