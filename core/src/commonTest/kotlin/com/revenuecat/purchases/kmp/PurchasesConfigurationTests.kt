package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals


class PurchasesConfigurationTests {

    @Test
    fun testCorrectlyProvidesStoreKitVersionIfMatching() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
            storeKitVersion = StoreKitVersion.DEFAULT
        }

        assertEquals(config.storeKitVersionToUse(), StoreKitVersion.DEFAULT)
    }

    @Test
    fun testCorrectlyProvidesStoreKitVersionFromPurchasesAreCompletedByIfConflicting() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_2)
            storeKitVersion = StoreKitVersion.STOREKIT_1
        }

        assertEquals(config.storeKitVersionToUse(), StoreKitVersion.STOREKIT_2)
    }
}