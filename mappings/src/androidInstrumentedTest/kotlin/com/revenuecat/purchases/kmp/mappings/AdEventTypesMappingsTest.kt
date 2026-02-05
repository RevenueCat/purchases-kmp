package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import com.revenuecat.purchases.kmp.models.AdRevenuePrecision
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalRevenueCatApi::class)
class AdEventTypesMappingsTest {

    @Test
    fun `AdMediatorName converts to Android type`() {
        val kmpMediator = AdMediatorName.AD_MOB
        val androidMediator = kmpMediator.toAndroid()

        assertNotNull(androidMediator)
        assertEquals("AdMob", androidMediator.value)
    }

    @Test
    fun `AdRevenuePrecision converts to Android type`() {
        val kmpPrecision = AdRevenuePrecision.EXACT
        val androidPrecision = kmpPrecision.toAndroid()

        assertNotNull(androidPrecision)
        assertEquals("exact", androidPrecision.value)
    }

    @Test
    fun `AdDisplayedData converts to Android type`() {
        val kmpData = AdDisplayedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.AD_MOB,
            placement = "banner",
            adUnitId = "ad-unit-123",
            impressionId = "impression-456",
        )

        val androidData = kmpData.toAndroid()

        assertNotNull(androidData)
        assertEquals("TestNetwork", androidData.networkName)
        assertEquals("AdMob", androidData.mediatorName.value)
        assertEquals("banner", androidData.placement)
        assertEquals("ad-unit-123", androidData.adUnitId)
        assertEquals("impression-456", androidData.impressionId)
    }

    @Test
    fun `AdOpenedData converts to Android type`() {
        val kmpData = AdOpenedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.APP_LOVIN,
            placement = null,
            adUnitId = "ad-unit-789",
            impressionId = "impression-012",
        )

        val androidData = kmpData.toAndroid()

        assertNotNull(androidData)
        assertEquals("TestNetwork", androidData.networkName)
        assertEquals("AppLovin", androidData.mediatorName.value)
        assertEquals(null, androidData.placement)
        assertEquals("ad-unit-789", androidData.adUnitId)
        assertEquals("impression-012", androidData.impressionId)
    }

    @Test
    fun `AdRevenueData converts to Android type`() {
        val kmpData = AdRevenueData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.AD_MOB,
            placement = "interstitial",
            adUnitId = "ad-unit-999",
            impressionId = "impression-111",
            revenueMicros = 5000000L,
            currency = "USD",
            precision = AdRevenuePrecision.ESTIMATED,
        )

        val androidData = kmpData.toAndroid()

        assertNotNull(androidData)
        assertEquals("TestNetwork", androidData.networkName)
        assertEquals("interstitial", androidData.placement)
        assertEquals(5000000L, androidData.revenueMicros)
        assertEquals("USD", androidData.currency)
        assertEquals("estimated", androidData.precision.value)
    }

    @Test
    fun `AdLoadedData converts to Android type`() {
        val kmpData = AdLoadedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName("CustomMediator"),
            placement = "rewarded",
            adUnitId = "ad-unit-222",
            impressionId = "impression-333",
        )

        val androidData = kmpData.toAndroid()

        assertNotNull(androidData)
        assertEquals("TestNetwork", androidData.networkName)
        assertEquals("CustomMediator", androidData.mediatorName.value)
        assertEquals("rewarded", androidData.placement)
        assertEquals("ad-unit-222", androidData.adUnitId)
        assertEquals("impression-333", androidData.impressionId)
    }

    @Test
    fun `AdFailedToLoadData converts to Android type`() {
        val kmpData = AdFailedToLoadData(
            mediatorName = AdMediatorName.AD_MOB,
            placement = "banner",
            adUnitId = "ad-unit-444",
            mediatorErrorCode = 3,
        )

        val androidData = kmpData.toAndroid()

        assertNotNull(androidData)
        assertEquals("AdMob", androidData.mediatorName.value)
        assertEquals("banner", androidData.placement)
        assertEquals("ad-unit-444", androidData.adUnitId)
        assertEquals(3, androidData.mediatorErrorCode)
    }

    @Test
    fun `AdFailedToLoadData with null error code converts to Android type`() {
        val kmpData = AdFailedToLoadData(
            mediatorName = AdMediatorName.APP_LOVIN,
            placement = null,
            adUnitId = "ad-unit-555",
            mediatorErrorCode = null,
        )

        val androidData = kmpData.toAndroid()

        assertNotNull(androidData)
        assertEquals(null, androidData.mediatorErrorCode)
        assertEquals(null, androidData.placement)
    }
}
