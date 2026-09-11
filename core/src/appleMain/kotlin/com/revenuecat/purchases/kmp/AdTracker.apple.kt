package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSNumber
import com.revenuecat.purchases.kn.core.additional.AdTracking
import com.revenuecat.purchases.kn.core.additional.AppleApiAvailability

@ExperimentalRevenueCatApi
@OptIn(ExperimentalForeignApi::class, InternalRevenueCatApi::class)
public actual class AdTracker internal constructor() {
    public actual fun trackAdDisplayed(data: AdDisplayedData) {
        if (!isAvailable()) return
        AdTracking.trackAdDisplayedWithNetworkName(
            networkName = data.networkName,
            mediatorName = data.mediatorName.value,
            adFormat = data.adFormat.value,
            placement = data.placement,
            adUnitId = data.adUnitId,
            impressionId = data.impressionId,
        )
    }

    public actual fun trackAdOpened(data: AdOpenedData) {
        if (!isAvailable()) return
        AdTracking.trackAdOpenedWithNetworkName(
            networkName = data.networkName,
            mediatorName = data.mediatorName.value,
            adFormat = data.adFormat.value,
            placement = data.placement,
            adUnitId = data.adUnitId,
            impressionId = data.impressionId,
        )
    }

    public actual fun trackAdRevenue(data: AdRevenueData) {
        if (!isAvailable()) return
        AdTracking.trackAdRevenueWithNetworkName(
            networkName = data.networkName,
            mediatorName = data.mediatorName.value,
            adFormat = data.adFormat.value,
            placement = data.placement,
            adUnitId = data.adUnitId,
            impressionId = data.impressionId,
            revenueMicros = data.revenueMicros,
            currency = data.currency,
            precision = data.precision.value,
        )
    }

    public actual fun trackAdLoaded(data: AdLoadedData) {
        if (!isAvailable()) return
        AdTracking.trackAdLoadedWithNetworkName(
            networkName = data.networkName,
            mediatorName = data.mediatorName.value,
            adFormat = data.adFormat.value,
            placement = data.placement,
            adUnitId = data.adUnitId,
            impressionId = data.impressionId,
        )
    }

    public actual fun trackAdFailedToLoad(data: AdFailedToLoadData) {
        if (!isAvailable()) return
        AdTracking.trackAdFailedToLoadWithMediatorName(
            mediatorName = data.mediatorName.value,
            adFormat = data.adFormat.value,
            placement = data.placement,
            adUnitId = data.adUnitId,
            mediatorErrorCode = data.mediatorErrorCode?.let { NSNumber(int = it) },
        )
    }

    private fun isAvailable(): Boolean {
        if (appleApiAvailability.isAdTrackingAPIAvailable()) return true
        Purchases.logHandler.w("Purchases", AD_TRACKING_UNAVAILABLE_MESSAGE)
        return false
    }
}

private val appleApiAvailability = AppleApiAvailability()

internal const val AD_TRACKING_UNAVAILABLE_MESSAGE =
    "Ad tracking and reward verification require iOS 15.0+ or watchOS 8.0+. Current API is unavailable."
