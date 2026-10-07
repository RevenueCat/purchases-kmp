package com.revenuecat.purchases.kmp.mappings

import platform.Foundation.NSDecimalNumber
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PriceTest {

    // Regression test: the decimal-to-micros conversion used longValue, which is 32-bit on
    // watchosArm64 (arm64_32) and truncates amounts beyond 2147.48 currency units in micros.
    @Test
    fun `amountMicros preserves values beyond 32 bits`() {
        val price = priceOrNull(
            currencyCode = "KRW",
            formatted = "₩10,000",
            amountDecimal = NSDecimalNumber(string = "10000"),
        )

        assertEquals(10_000_000_000L, price?.amountMicros)
        assertEquals("KRW", price?.currencyCode)
    }

    @Test
    fun `returns null when formatted or amount is missing`() {
        assertNull(priceOrNull(currencyCode = "USD", formatted = null, amountDecimal = NSDecimalNumber.one))
        assertNull(priceOrNull(currencyCode = "USD", formatted = "$1.00", amountDecimal = null))
    }
}
