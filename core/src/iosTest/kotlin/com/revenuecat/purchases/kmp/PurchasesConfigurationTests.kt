package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.StoreKitVersion
import kotlin.test.Test
import kotlin.test.assertEquals


class PurchasesConfigurationTests {

    @Test
    fun `storeKitVersion provides the correct result if the provided values are matching`() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
            storeKitVersion = StoreKitVersion.DEFAULT
        }

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersion)
    }

    @Test
    fun `storeKitVersion provides the correct result if storeKitVersion is missing`() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
        }

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersion)
    }

    @Test
    fun `storeKitVersion provides the correct result if PurchasesAreCompletedBy is missing`() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            storeKitVersion = StoreKitVersion.STOREKIT_1
        }

        assertEquals(StoreKitVersion.STOREKIT_1, config.storeKitVersion)
    }

    @Test
    fun `storeKitVersion provides the DEFAULT value if StoreKitVersion is not provided`() {
        val config = PurchasesConfiguration(apiKey = "abc123")

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersion)
    }

    @Test
    fun `storeKitVersion provides the value from PurchasesAreCompletedBy if conflicting values are provided`() {
        Purchases.logHandler = PrintLnLogHandler
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_2)
            storeKitVersion = StoreKitVersion.STOREKIT_1
        }

        assertEquals(StoreKitVersion.STOREKIT_2, config.storeKitVersion)
    }
}
