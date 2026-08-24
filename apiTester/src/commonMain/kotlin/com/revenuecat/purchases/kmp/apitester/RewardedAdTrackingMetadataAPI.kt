package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.AdFormat
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.RewardedAdTrackingMetadata

@Suppress("unused", "UNUSED_VARIABLE")
private class RewardedAdTrackingMetadataAPI {
    fun check(metadata: RewardedAdTrackingMetadata) {
        val networkName: String? = metadata.networkName
        val mediatorName: AdMediatorName = metadata.mediatorName
        val adFormat: AdFormat = metadata.adFormat
        val placement: String? = metadata.placement
        val adUnitId: String = metadata.adUnitId
        val impressionId: String = metadata.impressionId
    }

    fun checkConstructor() {
        val metadata = RewardedAdTrackingMetadata(
            networkName = "network-name",
            mediatorName = AdMediatorName.AD_MOB,
            adFormat = AdFormat.REWARDED,
            placement = "placement",
            adUnitId = "ad-unit-id",
            impressionId = "impression-id",
        )
        check(metadata)
    }
}
