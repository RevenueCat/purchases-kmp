package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.AdRewardConfiguration
import com.revenuecat.purchases.kmp.models.ConfiguredAdRewards
import com.revenuecat.purchases.kn.core.additional.ConfiguredRewardKindEntitlement
import com.revenuecat.purchases.kn.core.additional.ConfiguredRewardKindVirtualCurrency
import kotlin.time.Duration.Companion.seconds
import com.revenuecat.purchases.kn.core.additional.AdRewardConfiguration as AppleAdRewardConfiguration
import com.revenuecat.purchases.kn.core.additional.ConfiguredAdRewards as AppleConfiguredAdRewards

/** Maps the native SDK's validated configured rewards, preserving their order. */
@ExperimentalRevenueCatApi
public fun AppleConfiguredAdRewards.toConfiguredAdRewards(): ConfiguredAdRewards {
    val primary = reward() ?: return ConfiguredAdRewards.NO_CONFIGURED_REWARD
    @Suppress("UNCHECKED_CAST")
    val additional = moreRewards() as List<AppleAdRewardConfiguration>
    return ConfiguredAdRewards(primary.toAdRewardConfiguration(), additional.map { it.toAdRewardConfiguration() })
}

@OptIn(ExperimentalRevenueCatApi::class)
private fun AppleAdRewardConfiguration.toAdRewardConfiguration(): AdRewardConfiguration = when (kind()) {
    ConfiguredRewardKindVirtualCurrency -> {
        val code = virtualCurrencyCode()
        val amount = virtualCurrencyAmount().toLong()
        if (!code.isNullOrEmpty() && amount in 1..Int.MAX_VALUE.toLong()) {
            AdRewardConfiguration.VirtualCurrency(code, amount.toInt())
        } else {
            AdRewardConfiguration.UnsupportedReward
        }
    }
    ConfiguredRewardKindEntitlement -> {
        val identifier = entitlementIdentifier()
        val seconds = entitlementDurationSeconds()
        if (!identifier.isNullOrEmpty() && seconds.isFinite() && seconds > 0 && seconds.seconds.isFinite()) {
            AdRewardConfiguration.Entitlement(identifier, seconds.seconds)
        } else {
            AdRewardConfiguration.UnsupportedReward
        }
    }
    else -> AdRewardConfiguration.UnsupportedReward
}
