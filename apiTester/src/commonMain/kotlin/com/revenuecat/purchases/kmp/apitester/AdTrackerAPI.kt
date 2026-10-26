package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.AdTracker
import com.revenuecat.purchases.kmp.models.AdFormat
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.AdRewardEarnedUnverifiedData

@Suppress("unused", "UNUSED_VARIABLE")
private class AdTrackerAPI {
    fun checkTrackAdRewardEarnedUnverified(adTracker: AdTracker) {
        val data = AdRewardEarnedUnverifiedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.AD_MOB,
            adFormat = AdFormat.REWARDED,
            placement = "rewarded",
            adUnitId = "ad-unit-123",
            impressionId = "impression-456",
        )

        val networkName: String? = data.networkName
        val mediatorName: AdMediatorName = data.mediatorName
        val adFormat: AdFormat = data.adFormat
        val placement: String? = data.placement
        val adUnitId: String = data.adUnitId
        val impressionId: String = data.impressionId

        adTracker.trackAdRewardEarnedUnverified(data)
    }
}
