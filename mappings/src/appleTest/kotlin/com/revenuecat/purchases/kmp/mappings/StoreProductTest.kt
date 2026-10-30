package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kn.core.additional.StoreProductIdentifier
import platform.darwin.NSObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StoreProductTest {

    @Test
    fun `id is the product identifier for StoreKit 1 products`() {
        val product = FakeSKProduct().toRCStoreProduct().toStoreProduct()

        assertEquals("monthly", product.id)
    }

    @Test
    fun `StoreProductIdentifier returns null for non-StoreProduct objects`() {
        assertNull(StoreProductIdentifier.identifier(NSObject()))
    }

    @Test
    fun `installmentsInfo is null for StoreKit 1 products`() {
        val product = FakeSKProduct().toRCStoreProduct().toStoreProduct()

        assertNull(product.installmentsInfo)
    }
}
