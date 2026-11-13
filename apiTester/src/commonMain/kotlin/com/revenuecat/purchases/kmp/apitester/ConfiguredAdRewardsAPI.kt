package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ktx.awaitAdRewards
import com.revenuecat.purchases.kmp.models.AdRewardConfiguration
import com.revenuecat.purchases.kmp.models.ConfiguredAdRewards
import kotlin.time.Duration

@OptIn(ExperimentalRevenueCatApi::class)
@Suppress("unused", "UNUSED_VARIABLE")
private class ConfiguredAdRewardsAPI {
    fun check(purchases: Purchases, rewards: ConfiguredAdRewards) {
        purchases.getAdRewards("ad-unit") { result: ConfiguredAdRewards? -> }
        val primary: AdRewardConfiguration? = rewards.reward
        val additional: List<AdRewardConfiguration> = rewards.moreRewards
        val absent: ConfiguredAdRewards = ConfiguredAdRewards.NO_CONFIGURED_REWARD
        when (primary) {
            is AdRewardConfiguration.VirtualCurrency -> {
                val code: String = primary.code
                val amount: Int = primary.amount
            }
            is AdRewardConfiguration.Entitlement -> {
                val identifier: String = primary.identifier
                val duration: Duration = primary.duration
                val seconds: Long = primary.durationInSeconds
            }
            AdRewardConfiguration.UnsupportedReward, null -> Unit
        }
    }

    suspend fun checkCoroutines(purchases: Purchases) {
        val rewards: ConfiguredAdRewards? = purchases.awaitAdRewards("ad-unit")
    }
}
