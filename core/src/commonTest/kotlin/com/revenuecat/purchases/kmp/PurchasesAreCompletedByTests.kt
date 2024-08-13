package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals

class PurchasesAreCompletedByTests {

    @Test
    fun testStoreKitVersionWithRevenueCatIsDefault() {
        val purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
        val skVersion = purchasesAreCompletedBy.storeKitVersion()

        assertEquals(StoreKitVersion.DEFAULT, skVersion)
    }

    @Test
    fun testStoreKitVersionWithMyAppIsCorrect() {
        val purchasesAreCompletedBy1 = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_1)
        val skVersion1 = purchasesAreCompletedBy1.storeKitVersion()

        val purchasesAreCompletedBy2 = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_2)
        val skVersion2 = purchasesAreCompletedBy2.storeKitVersion()

        val purchasesAreCompletedByDefault = PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)
        val skVersionDefault = purchasesAreCompletedByDefault.storeKitVersion()

        assertEquals(StoreKitVersion.STOREKIT_1, skVersion1)
        assertEquals(StoreKitVersion.STOREKIT_2, skVersion2)
        assertEquals(StoreKitVersion.DEFAULT, skVersionDefault)
    }

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