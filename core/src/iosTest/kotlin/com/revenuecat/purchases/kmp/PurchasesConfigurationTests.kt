package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.PrintLnLogHandler
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import com.revenuecat.purchases.kmp.StoreKitVersion
import kotlin.test.Test
import kotlin.test.assertEquals


class PurchasesConfigurationTests {

    @Test
    fun `storeKitVersionToUse provides the correct result if the provided values are matching`() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
            storeKitVersion = StoreKitVersion.DEFAULT
        }

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersionToUse())
    }

    @Test
    fun `storeKitVersionToUse provides the correct result if storeKitVersion is missing`() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
        }

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersionToUse())
    }

    @Test
    fun `storeKitVersionToUse provides the correct result if PurchasesAreCompletedBy is missing`() {
        val config = PurchasesConfiguration(apiKey = "abc123") {
            storeKitVersion = StoreKitVersion.STOREKIT_1
        }

        assertEquals(StoreKitVersion.STOREKIT_1, config.storeKitVersionToUse())
    }

    @Test
    fun `storeKitVersionToUse provides the DEFAULT value if StoreKitVersion is not provided`() {
        val config = PurchasesConfiguration(apiKey = "abc123")

        assertEquals(StoreKitVersion.DEFAULT, config.storeKitVersionToUse())
    }

    @Test
    fun `storeKitVersionToUse provides the value from PurchasesAreCompletedBy if conflicting values are provided`() {
        Purchases.logHandler = PrintLnLogHandler
        val config = PurchasesConfiguration(apiKey = "abc123") {
            purchasesAreCompletedBy = PurchasesAreCompletedBy.MyApp(StoreKitVersion.STOREKIT_2)
            storeKitVersion = StoreKitVersion.STOREKIT_1
        }

        assertEquals(StoreKitVersion.STOREKIT_2, config.storeKitVersionToUse())
    }
}
