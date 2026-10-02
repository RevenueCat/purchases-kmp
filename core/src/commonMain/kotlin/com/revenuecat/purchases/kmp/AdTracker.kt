package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import com.revenuecat.purchases.kmp.models.AdRewardPromptAcceptedData
import com.revenuecat.purchases.kmp.models.AdRewardPromptShownData

/**
 * Handles tracking of ad events including impressions, clicks, revenue, and loading states.
 *
 * Access this via [Purchases.adTracker].
 */
public expect class AdTracker {
    /**
     * Tracks when an ad is displayed to the user.
     *
     * @param data The ad display event data.
     */
    public fun trackAdDisplayed(data: AdDisplayedData)

    /**
     * Tracks when an ad is opened/clicked by the user.
     *
     * @param data The ad opened event data.
     */
    public fun trackAdOpened(data: AdOpenedData)

    /**
     * Tracks ad revenue generated.
     *
     * @param data The ad revenue event data.
     */
    public fun trackAdRevenue(data: AdRevenueData)

    /**
     * Tracks when an ad successfully loads.
     *
     * @param data The ad loaded event data.
     */
    public fun trackAdLoaded(data: AdLoadedData)

    /**
     * Tracks when an ad fails to load.
     *
     * @param data The ad failed to load event data.
     */
    public fun trackAdFailedToLoad(data: AdFailedToLoadData)

    /**
     * Tracks when a rewarded ad prompt is shown to the user, such as a "Watch an ad to earn coins" button.
     * This event is manual-only: only the app knows when the prompt is presented.
     *
     * @param data The rewarded ad prompt shown event data.
     */
    public fun trackRewardedAdPromptShown(data: AdRewardPromptShownData)

    /**
     * Tracks when the user accepts a rewarded ad prompt, such as tapping a "Watch an ad to earn coins" button.
     * This event is manual-only: only the app knows when the prompt is accepted.
     *
     * @param data The rewarded ad prompt accepted event data.
     */
    public fun trackRewardedAdPromptAccepted(data: AdRewardPromptAcceptedData)
}
