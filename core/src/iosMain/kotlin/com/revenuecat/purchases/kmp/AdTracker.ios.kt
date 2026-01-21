package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData

/**
 * iOS implementation of [AdTracker]. Ad tracking is not yet supported on iOS.
 */
@ExperimentalRevenueCatApi
public actual class AdTracker {
    public actual fun trackAdDisplayed(data: AdDisplayedData): Unit =
        error("Ad tracking is not yet supported in iOS KMP")

    public actual fun trackAdOpened(data: AdOpenedData): Unit =
        error("Ad tracking is not yet supported in iOS KMP")

    public actual fun trackAdRevenue(data: AdRevenueData): Unit =
        error("Ad tracking is not yet supported in iOS KMP")

    public actual fun trackAdLoaded(data: AdLoadedData): Unit =
        error("Ad tracking is not yet supported in iOS KMP")

    public actual fun trackAdFailedToLoad(data: AdFailedToLoadData): Unit =
        error("Ad tracking is not yet supported in iOS KMP")
}
