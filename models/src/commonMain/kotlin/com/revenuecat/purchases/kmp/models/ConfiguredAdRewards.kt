package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi

/**
 * Rewards configured before an ad is watched, rather than rewards granted after verification.
 *
 * A null accessor result means configuration is unavailable or malformed. [NO_CONFIGURED_REWARD]
 * means configuration resolved successfully and no reward is configured for the ad unit.
 *
 * @property reward The primary reward, or null when no reward is configured.
 * @property moreRewards Additional rewards in configured order. Empty when [reward] is null.
 */
@ExperimentalRevenueCatApi
public class ConfiguredAdRewards(
    public val reward: AdRewardConfiguration?,
    moreRewards: List<AdRewardConfiguration> = emptyList(),
) {
    public val moreRewards: List<AdRewardConfiguration> = moreRewards.toList()

    init {
        require(reward != null || moreRewards.isEmpty()) { "Additional rewards require a primary reward" }
    }

    public companion object {
        /** Configuration resolved successfully with no reward configured for this ad unit. */
        public val NO_CONFIGURED_REWARD: ConfiguredAdRewards = ConfiguredAdRewards(null)
    }
}
