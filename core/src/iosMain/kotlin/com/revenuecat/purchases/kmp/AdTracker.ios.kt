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

/**
 * iOS implementation of [AdTracker] that bridges to CommonFunctionality.
 */
@ExperimentalRevenueCatApi
@OptIn(ExperimentalForeignApi::class)
public actual class AdTracker {
    public actual fun trackAdDisplayed(data: AdDisplayedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            "networkName" to data.networkName,
            "mediatorName" to data.mediatorName.value,
            "placement" to data.placement,
            "adUnitId" to data.adUnitId,
            "impressionId" to data.impressionId
        )

        RCCommonFunctionality.trackAdDisplayed(adData) { error ->
            error?.let {
                Purchases.logHandler.e("Purchases", "Failed to track ad displayed: ${it.message}", null)
            }
        }
    }

    public actual fun trackAdOpened(data: AdOpenedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            "networkName" to data.networkName,
            "mediatorName" to data.mediatorName.value,
            "placement" to data.placement,
            "adUnitId" to data.adUnitId,
            "impressionId" to data.impressionId
        )

        RCCommonFunctionality.trackAdOpened(adData) { error ->
            error?.let {
                Purchases.logHandler.e("Purchases", "Failed to track ad opened: ${it.message}", null)
            }
        }
    }

    public actual fun trackAdRevenue(data: AdRevenueData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            "networkName" to data.networkName,
            "mediatorName" to data.mediatorName.value,
            "placement" to data.placement,
            "adUnitId" to data.adUnitId,
            "impressionId" to data.impressionId,
            "revenueMicros" to NSNumber(data.revenueMicros),
            "currency" to data.currency,
            "precision" to data.precision.value
        )

        RCCommonFunctionality.trackAdRevenue(adData) { error ->
            error?.let {
                Purchases.logHandler.e("Purchases", "Failed to track ad revenue: ${it.message}", null)
            }
        }
    }

    public actual fun trackAdLoaded(data: AdLoadedData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = mapOf<Any?, Any?>(
            "networkName" to data.networkName,
            "mediatorName" to data.mediatorName.value,
            "placement" to data.placement,
            "adUnitId" to data.adUnitId,
            "impressionId" to data.impressionId
        )

        RCCommonFunctionality.trackAdLoaded(adData) { error ->
            error?.let {
                Purchases.logHandler.e("Purchases", "Failed to track ad loaded: ${it.message}", null)
            }
        }
    }

    public actual fun trackAdFailedToLoad(data: AdFailedToLoadData) {
        if (!IOSAPIAvailabilityChecker().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        val adData = buildMap<Any?, Any?> {
            put("networkName", data.networkName)
            put("mediatorName", data.mediatorName.value)
            put("placement", data.placement)
            put("adUnitId", data.adUnitId)
            data.mediatorErrorCode?.let { put("mediatorErrorCode", NSNumber(it)) }
        }

        RCCommonFunctionality.trackAdFailedToLoad(adData) { error ->
            error?.let {
                Purchases.logHandler.e("Purchases", "Failed to track ad failed to load: ${it.message}", null)
            }
        }
    }
}
