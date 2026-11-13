package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.ExperimentalPreviewRevenueCatPurchasesAPI
import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.AdRewardConfiguration
import com.revenuecat.purchases.kmp.models.ConfiguredAdRewards
import com.revenuecat.purchases.ads.AdRewardConfiguration as AndroidAdRewardConfiguration
import com.revenuecat.purchases.ads.ConfiguredAdRewards as AndroidConfiguredAdRewards

/** Maps native Android configured rewards without maintaining another configuration cache. */
@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AndroidConfiguredAdRewards.toConfiguredAdRewards(): ConfiguredAdRewards {
    val primary = reward ?: return ConfiguredAdRewards.NO_CONFIGURED_REWARD
    return ConfiguredAdRewards(primary.toAdRewardConfiguration(), moreRewards.map { it.toAdRewardConfiguration() })
}

@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class, ExperimentalRevenueCatApi::class)
private fun AndroidAdRewardConfiguration.toAdRewardConfiguration(): AdRewardConfiguration = when (this) {
    is AndroidAdRewardConfiguration.VirtualCurrency -> AdRewardConfiguration.VirtualCurrency(code, amount)
    is AndroidAdRewardConfiguration.Entitlement -> AdRewardConfiguration.Entitlement(identifier, duration)
    else -> AdRewardConfiguration.UnsupportedReward
}
