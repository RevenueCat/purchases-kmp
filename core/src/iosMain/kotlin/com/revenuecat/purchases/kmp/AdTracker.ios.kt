package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.mappings.toIos
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import kotlinx.cinterop.ExperimentalForeignApi
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdTracker
import swiftPMImport.com.revenuecat.purchases.kn.core.additional.AppleApiAvailability

@ExperimentalRevenueCatApi
@OptIn(ExperimentalForeignApi::class)
public actual class AdTracker internal constructor(
    private val iosAdTracker: RCAdTracker
) {
    public actual fun trackAdDisplayed(data: AdDisplayedData) {
        if (!AppleApiAvailability().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        iosAdTracker.trackAdDisplayed(data.toIos())
    }

    public actual fun trackAdOpened(data: AdOpenedData) {
        if (!AppleApiAvailability().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        iosAdTracker.trackAdOpened(data.toIos())
    }

    public actual fun trackAdRevenue(data: AdRevenueData) {
        if (!AppleApiAvailability().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        iosAdTracker.trackAdRevenue(data.toIos())
    }

    public actual fun trackAdLoaded(data: AdLoadedData) {
        if (!AppleApiAvailability().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        iosAdTracker.trackAdLoaded(data.toIos())
    }

    public actual fun trackAdFailedToLoad(data: AdFailedToLoadData) {
        if (!AppleApiAvailability().isAdTrackingAPIAvailable()) {
            Purchases.logHandler.w("Purchases", "Ad tracking requires iOS 15.0+. Current API is unavailable.")
            return
        }

        iosAdTracker.trackAdFailedToLoad(data.toIos())
    }
}
