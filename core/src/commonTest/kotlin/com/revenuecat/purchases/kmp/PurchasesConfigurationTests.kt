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

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersionToUse())
    }

    @Test
    fun testCorrectlyProvidesStoreKitVersionIfMissingStoreKitVersion() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
        }

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersionToUse())
    }

    @Test
    fun testCorrectlyProvidesStoreKitVersionIfMissingPurchasesAreCompletedBy() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            storeKitVersion = StoreKitVersion.STOREKIT_1
        }

        assertEquals(StoreKitVersion.STOREKIT_1, config.storeKitVersionToUse())
    }

    @Test
    fun testCorrectlyProvidesStoreKitVersionIfMissingAllStoreKitVersions() {
        val config = PurchasesConfiguration(apiKey = "abc123")

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersionToUse())
    }

    @Test
    fun testCorrectlyProvidesStoreKitVersionFromPurchasesAreCompletedByIfConflictingValuesProvided() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_2)
            storeKitVersion = StoreKitVersion.STOREKIT_1
        }

        assertEquals(StoreKitVersion.STOREKIT_2, config.storeKitVersionToUse())
    }
}