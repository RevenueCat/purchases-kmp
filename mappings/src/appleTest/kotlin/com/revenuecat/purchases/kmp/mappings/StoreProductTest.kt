package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kn.core.RCStoreProduct
import com.revenuecat.purchases.kn.core.additional.StoreProductIdentifier
import com.revenuecat.purchases.kn.core.create
import platform.darwin.NSObject
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSLocale
import platform.StoreKit.SKProduct
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StoreProductTest {

    @Test
    fun `id is the product identifier for StoreKit 1 products`() {
        val product = RCStoreProduct.create(sk1Product = FakeSKProduct() as objcnames.classes.SKProduct).toStoreProduct()

        assertEquals("monthly", product.id)
    }

    @Test
    fun `StoreProductIdentifier returns null for non-StoreProduct objects`() {
        assertNull(StoreProductIdentifier.identifier(NSObject()))
    }

    @Test
    fun `installmentsInfo is null for StoreKit 1 products`() {
        val product = RCStoreProduct.create(sk1Product = FakeSKProduct() as objcnames.classes.SKProduct).toStoreProduct()

        assertNull(product.installmentsInfo)
    }

    private class FakeSKProduct : SKProduct() {
        override fun productIdentifier(): String = "monthly"
        override fun localizedTitle(): String = "Monthly"
        override fun localizedDescription(): String = "Monthly subscription"
        override fun price(): NSDecimalNumber = NSDecimalNumber(string = "9.99")
        override fun priceLocale(): NSLocale = NSLocale(localeIdentifier = "en_US")
    }
}
