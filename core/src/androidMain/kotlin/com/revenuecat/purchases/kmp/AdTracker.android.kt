package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.ads.events.AdTracker as AndroidAdTracker
import com.revenuecat.purchases.kmp.mappings.toAndroid
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData

/**
 * Android implementation of [AdTracker] that delegates to the native Android SDK.
 */
@ExperimentalRevenueCatApi
@OptIn(com.revenuecat.purchases.ExperimentalPreviewRevenueCatPurchasesAPI::class)
public actual class AdTracker internal constructor(
    private val androidAdTracker: AndroidAdTracker
) {
    public actual fun trackAdDisplayed(data: AdDisplayedData) {
        androidAdTracker.trackAdDisplayed(data.toAndroid())
    }

    public actual fun trackAdOpened(data: AdOpenedData) {
        androidAdTracker.trackAdOpened(data.toAndroid())
    }

    public actual fun trackAdRevenue(data: AdRevenueData) {
        androidAdTracker.trackAdRevenue(data.toAndroid())
    }

    public actual fun trackAdLoaded(data: AdLoadedData) {
        androidAdTracker.trackAdLoaded(data.toAndroid())
    }

    public actual fun trackAdFailedToLoad(data: AdFailedToLoadData) {
        androidAdTracker.trackAdFailedToLoad(data.toAndroid())
    }
}
