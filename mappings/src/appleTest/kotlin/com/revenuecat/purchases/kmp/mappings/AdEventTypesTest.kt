package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.AdFormat
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.AdRevenueData
import com.revenuecat.purchases.kmp.models.AdRevenuePrecision
import platform.darwin.NSIntegerMax
import kotlin.test.Test
import kotlin.test.assertEquals

class AdEventTypesTest {

    // Regression test: revenueMicros used to be clamped to the 32-bit Int range on all Apple
    // targets, corrupting large revenues (common in low-unit currencies) on 64-bit platforms.
    @Test
    fun `revenueMicros preserves values beyond 32 bits where NSInteger is 64-bit`() {
        val largeRevenueMicros = 10_000_000_000L
        if (NSIntegerMax.toLong() < largeRevenueMicros) return // arm64_32: clamping is expected

        val mapped = adRevenueData(revenueMicros = largeRevenueMicros).toIos()

        assertEquals(largeRevenueMicros, mapped.revenueMicros().toLong())
    }

    @Test
    fun `revenueMicros never exceeds the platform NSInteger range`() {
        val mapped = adRevenueData(revenueMicros = Long.MAX_VALUE).toIos()

        assertEquals(NSIntegerMax.toLong(), mapped.revenueMicros().toLong())
    }

    private fun adRevenueData(revenueMicros: Long) = AdRevenueData(
        mediatorName = AdMediatorName.APP_LOVIN,
        adFormat = AdFormat.BANNER,
        placement = null,
        adUnitId = "adUnitId",
        impressionId = "impressionId",
        revenueMicros = revenueMicros,
        currency = "USD",
        precision = AdRevenuePrecision.EXACT,
    )
}
