package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalRevenueCatApi::class)
class AdEventTypesTest {

    @Test
    fun `AdMediatorName with predefined value`() {
        val adMob = AdMediatorName.AD_MOB
        assertEquals("AdMob", adMob.value)
    }

    @Test
    fun `AdMediatorName with custom value`() {
        val custom = AdMediatorName("CustomMediator")
        assertEquals("CustomMediator", custom.value)
    }

    @Test
    fun `AdRevenuePrecision with predefined value`() {
        val exact = AdRevenuePrecision.EXACT
        assertEquals("exact", exact.value)

        val estimated = AdRevenuePrecision.ESTIMATED
        assertEquals("estimated", estimated.value)
    }

    @Test
    fun `AdRevenuePrecision with custom value`() {
        val custom = AdRevenuePrecision("custom_precision")
        assertEquals("custom_precision", custom.value)
    }

    @Test
    fun `AdFormat with predefined value`() {
        val banner = AdFormat.BANNER
        assertEquals("banner", banner.value)

        val interstitial = AdFormat.INTERSTITIAL
        assertEquals("interstitial", interstitial.value)
    }

    @Test
    fun `AdFormat with custom value`() {
        val custom = AdFormat("custom_format")
        assertEquals("custom_format", custom.value)
    }

    @Test
    fun `AdDisplayedData can be constructed`() {
        val data = AdDisplayedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.AD_MOB,
            adFormat = AdFormat.BANNER,
            placement = "banner",
            adUnitId = "ad-unit-123",
            impressionId = "impression-456",
        )

        assertNotNull(data)
        assertEquals("TestNetwork", data.networkName)
        assertEquals(AdMediatorName.AD_MOB, data.mediatorName)
        assertEquals(AdFormat.BANNER, data.adFormat)
        assertEquals("banner", data.placement)
        assertEquals("ad-unit-123", data.adUnitId)
        assertEquals("impression-456", data.impressionId)
    }

    @Test
    fun `AdRevenueData can be constructed with all fields`() {
        val data = AdRevenueData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.APP_LOVIN,
            adFormat = AdFormat.INTERSTITIAL,
            placement = "interstitial",
            adUnitId = "ad-unit-789",
            impressionId = "impression-012",
            revenueMicros = 1000000L,
            currency = "USD",
            precision = AdRevenuePrecision.EXACT,
        )

        assertNotNull(data)
        assertEquals(AdFormat.INTERSTITIAL, data.adFormat)
        assertEquals(1000000L, data.revenueMicros)
        assertEquals("USD", data.currency)
        assertEquals(AdRevenuePrecision.EXACT, data.precision)
    }

    @Test
    fun `AdFailedToLoadData with null error code`() {
        val data = AdFailedToLoadData(
            mediatorName = AdMediatorName.AD_MOB,
            adFormat = AdFormat.REWARDED,
            placement = null,
            adUnitId = "ad-unit-999",
            mediatorErrorCode = null,
        )

        assertNotNull(data)
        assertEquals(AdFormat.REWARDED, data.adFormat)
        assertEquals(null, data.mediatorErrorCode)
        assertEquals(null, data.placement)
    }
}
