package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.models.Period
import com.revenuecat.purchases.models.Price
import com.revenuecat.purchases.models.TestStoreProduct
import kotlin.test.Test
import kotlin.test.assertNull

class StoreProductTest {

    @Test
    fun `installmentsInfo is always null on Android`() {
        val product = TestStoreProduct(
            id = "monthly",
            name = "Monthly",
            title = "Monthly (App)",
            description = "Monthly subscription",
            price = Price(formatted = "$9.99", amountMicros = 9_990_000, currencyCode = "USD"),
            period = Period(value = 1, unit = Period.Unit.MONTH, iso8601 = "P1M"),
        ).toStoreProduct()

        assertNull(product.installmentsInfo)
    }
}
