package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.IOSAPIAvailabilityChecker
import cocoapods.PurchasesHybridCommon.RCCommonFunctionality
import cocoapods.PurchasesHybridCommon.trackAdDisplayed
import cocoapods.PurchasesHybridCommon.trackAdFailedToLoad
import cocoapods.PurchasesHybridCommon.trackAdLoaded
import cocoapods.PurchasesHybridCommon.trackAdOpened
import cocoapods.PurchasesHybridCommon.trackAdRevenue
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSNumber

private const val KEY_NETWORK_NAME = "networkName"
private const val KEY_MEDIATOR_NAME = "mediatorName"
private const val KEY_AD_FORMAT = "adFormat"
private const val KEY_PLACEMENT = "placement"
private const val KEY_AD_UNIT_ID = "adUnitId"
private const val KEY_IMPRESSION_ID = "impressionId"
private const val KEY_REVENUE_MICROS = "revenueMicros"
private const val KEY_CURRENCY = "currency"
private const val KEY_PRECISION = "precision"
private const val KEY_MEDIATOR_ERROR_CODE = "mediatorErrorCode"

@ExperimentalRevenueCatApi
@OptIn(ExperimentalForeignApi::class)
public actual class AdTracker {
    public actual fun trackAdDisplayed(data: AdDisplayedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            KEY_NETWORK_NAME to data.networkName,
            KEY_MEDIATOR_NAME to data.mediatorName.value,
            KEY_AD_FORMAT to data.adFormat.value,
            KEY_PLACEMENT to data.placement,
            KEY_AD_UNIT_ID to data.adUnitId,
            KEY_IMPRESSION_ID to data.impressionId
        )

        RCCommonFunctionality.trackAdDisplayed(adData)
    }

    public actual fun trackAdOpened(data: AdOpenedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            KEY_NETWORK_NAME to data.networkName,
            KEY_MEDIATOR_NAME to data.mediatorName.value,
            KEY_AD_FORMAT to data.adFormat.value,
            KEY_PLACEMENT to data.placement,
            KEY_AD_UNIT_ID to data.adUnitId,
            KEY_IMPRESSION_ID to data.impressionId
        )

        RCCommonFunctionality.trackAdOpened(adData)
    }

    public actual fun trackAdRevenue(data: AdRevenueData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            KEY_NETWORK_NAME to data.networkName,
            KEY_MEDIATOR_NAME to data.mediatorName.value,
            KEY_AD_FORMAT to data.adFormat.value,
            KEY_PLACEMENT to data.placement,
            KEY_AD_UNIT_ID to data.adUnitId,
            KEY_IMPRESSION_ID to data.impressionId,
            KEY_REVENUE_MICROS to NSNumber(long = data.revenueMicros),
            KEY_CURRENCY to data.currency,
            KEY_PRECISION to data.precision.value
        )

        RCCommonFunctionality.trackAdRevenue(adData)
    }

    public actual fun trackAdLoaded(data: AdLoadedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            KEY_NETWORK_NAME to data.networkName,
            KEY_MEDIATOR_NAME to data.mediatorName.value,
            KEY_AD_FORMAT to data.adFormat.value,
            KEY_PLACEMENT to data.placement,
            KEY_AD_UNIT_ID to data.adUnitId,
            KEY_IMPRESSION_ID to data.impressionId
        )

        RCCommonFunctionality.trackAdLoaded(adData)
    }

    public actual fun trackAdFailedToLoad(data: AdFailedToLoadData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = buildMap<Any?, Any?> {
            put(KEY_MEDIATOR_NAME, data.mediatorName.value)
            put(KEY_AD_FORMAT, data.adFormat.value)
            put(KEY_PLACEMENT, data.placement)
            put(KEY_AD_UNIT_ID, data.adUnitId)
            data.mediatorErrorCode?.let { put(KEY_MEDIATOR_ERROR_CODE, NSNumber(int = it)) }
        }

        RCCommonFunctionality.trackAdFailedToLoad(adData)
    }
}
