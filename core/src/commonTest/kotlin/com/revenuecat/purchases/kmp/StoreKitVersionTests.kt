package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals

class StoreKitVersionTests {

    @Test
    fun testToHybridString() {
        val storeKit1 = StoreKitVersion.STOREKIT_1
        val storeKit1String = storeKit1.toHybridString()

        val storeKit2 = StoreKitVersion.STOREKIT_2
        val storeKit2String = storeKit2.toHybridString()

        val storeKitDefault = StoreKitVersion.DEFAULT
        val storeKitDefaultString = storeKitDefault.toHybridString()

        assertEquals(storeKit1String, "STOREKIT_1")
        assertEquals(storeKit2String, "STOREKIT_2")
        assertEquals(storeKitDefaultString, "DEFAULT")
    }
}