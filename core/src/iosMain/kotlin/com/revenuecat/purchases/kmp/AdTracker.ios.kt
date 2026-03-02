package com.revenuecat.purchases.kmp

import swiftPMImport.com.revenuecat.purchases.kn.core.IOSAPIAvailabilityChecker
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCommonFunctionality
import swiftPMImport.com.revenuecat.purchases.kn.core.trackAdDisplayed
import swiftPMImport.com.revenuecat.purchases.kn.core.trackAdFailedToLoad
import swiftPMImport.com.revenuecat.purchases.kn.core.trackAdLoaded
import swiftPMImport.com.revenuecat.purchases.kn.core.trackAdOpened
import swiftPMImport.com.revenuecat.purchases.kn.core.trackAdRevenue
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

        val adData = buildMap<Any?, Any?> {
            data.networkName?.let { put(KEY_NETWORK_NAME, it) }
            put(KEY_MEDIATOR_NAME, data.mediatorName.value)
            put(KEY_AD_FORMAT, data.adFormat.value)
            put(KEY_PLACEMENT, data.placement)
            put(KEY_AD_UNIT_ID, data.adUnitId)
            put(KEY_IMPRESSION_ID, data.impressionId)
        }

        RCCommonFunctionality.trackAdDisplayed(adData)
    }

    public actual fun trackAdOpened(data: AdOpenedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = buildMap<Any?, Any?> {
            data.networkName?.let { put(KEY_NETWORK_NAME, it) }
            put(KEY_MEDIATOR_NAME, data.mediatorName.value)
            put(KEY_AD_FORMAT, data.adFormat.value)
            put(KEY_PLACEMENT, data.placement)
            put(KEY_AD_UNIT_ID, data.adUnitId)
            put(KEY_IMPRESSION_ID, data.impressionId)
        }

        RCCommonFunctionality.trackAdOpened(adData)
    }

    public actual fun trackAdRevenue(data: AdRevenueData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = buildMap<Any?, Any?> {
            data.networkName?.let { put(KEY_NETWORK_NAME, it) }
            put(KEY_MEDIATOR_NAME, data.mediatorName.value)
            put(KEY_AD_FORMAT, data.adFormat.value)
            put(KEY_PLACEMENT, data.placement)
            put(KEY_AD_UNIT_ID, data.adUnitId)
            put(KEY_IMPRESSION_ID, data.impressionId)
            put(KEY_REVENUE_MICROS, NSNumber(long = data.revenueMicros))
            put(KEY_CURRENCY, data.currency)
            put(KEY_PRECISION, data.precision.value)
        }

        RCCommonFunctionality.trackAdRevenue(adData)
    }

    public actual fun trackAdLoaded(data: AdLoadedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = buildMap<Any?, Any?> {
            data.networkName?.let { put(KEY_NETWORK_NAME, it) }
            put(KEY_MEDIATOR_NAME, data.mediatorName.value)
            put(KEY_AD_FORMAT, data.adFormat.value)
            put(KEY_PLACEMENT, data.placement)
            put(KEY_AD_UNIT_ID, data.adUnitId)
            put(KEY_IMPRESSION_ID, data.impressionId)
        }

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
