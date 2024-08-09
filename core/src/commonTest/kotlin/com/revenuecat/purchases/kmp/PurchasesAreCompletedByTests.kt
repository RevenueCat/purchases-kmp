package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals

class PurchasesAreCompletedByTests {

    @Test
    fun testStoreKitVersionWithRevenueCatIsDefault() {
        val purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
        val skVersion = purchasesAreCompletedBy.storeKitVersion()

        assertEquals(skVersion, StoreKitVersion.DEFAULT)
    }

    @Test
    fun testStoreKitVersionWithMyAppIsCorrect() {
        val purchasesAreCompletedBy1 = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_1)
        val skVersion1 = purchasesAreCompletedBy1.storeKitVersion()

        val purchasesAreCompletedBy2 = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_2)
        val skVersion2 = purchasesAreCompletedBy2.storeKitVersion()

        val purchasesAreCompletedByDefault = PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)
        val skVersionDefault = purchasesAreCompletedByDefault.storeKitVersion()

        assertEquals(skVersion1, StoreKitVersion.STOREKIT_1)
        assertEquals(skVersion2, StoreKitVersion.STOREKIT_2)
        assertEquals(skVersionDefault, StoreKitVersion.DEFAULT)
    }

    @Test
    fun testToHybridString() {
        val purchasesAreCompletedByMyApp = PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)
        val myAppHybridString = purchasesAreCompletedByMyApp.toHybridString()

        val purchasesAreCompletedByRevenueCat = PurchasesAreCompletedBy.RevenueCat
        val revenuecatHybridString = purchasesAreCompletedByRevenueCat.toHybridString()

        assertEquals(myAppHybridString, "MY_APP")
        assertEquals(revenuecatHybridString, "REVENUECAT")
    }
}