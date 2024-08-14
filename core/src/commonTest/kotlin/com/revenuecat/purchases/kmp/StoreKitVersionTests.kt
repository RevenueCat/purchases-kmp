package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals

class StoreKitVersionTests {

    @Test
    fun `toHybridString returns the correct values`() {
        val storeKit1 = StoreKitVersion.STOREKIT_1
        val storeKit1String = storeKit1.toHybridString()

        val storeKit2 = StoreKitVersion.STOREKIT_2
        val storeKit2String = storeKit2.toHybridString()

        val storeKitDefault = StoreKitVersion.DEFAULT
        val storeKitDefaultString = storeKitDefault.toHybridString()

        assertEquals("STOREKIT_1", storeKit1String)
        assertEquals("STOREKIT_2", storeKit2String)
        assertEquals("DEFAULT", storeKitDefaultString)
    }
}