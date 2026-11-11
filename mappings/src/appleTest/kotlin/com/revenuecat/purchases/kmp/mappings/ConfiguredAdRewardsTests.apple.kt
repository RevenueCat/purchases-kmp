package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.AdRewardConfiguration
import com.revenuecat.purchases.kmp.models.ConfiguredAdRewards
import com.revenuecat.purchases.kn.core.additional.ConfiguredRewardKindEntitlement
import com.revenuecat.purchases.kn.core.additional.ConfiguredRewardKindUnsupportedReward
import com.revenuecat.purchases.kn.core.additional.ConfiguredRewardKindVirtualCurrency
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.time.Duration.Companion.seconds
import com.revenuecat.purchases.kn.core.additional.AdRewardConfiguration as AppleAdRewardConfiguration
import com.revenuecat.purchases.kn.core.additional.ConfiguredAdRewards as AppleConfiguredAdRewards

@OptIn(ExperimentalRevenueCatApi::class)
class ConfiguredAdRewardsTests {
    @Test
    fun `maps primary reward and ordered additional rewards across Swift interop`() {
        val primary = AppleAdRewardConfiguration(ConfiguredRewardKindVirtualCurrency, "coins", 10, null, 0.0)
        val entitlement = AppleAdRewardConfiguration(ConfiguredRewardKindEntitlement, null, 0, "pro", 1800.0)
        val unknown = AppleAdRewardConfiguration(ConfiguredRewardKindUnsupportedReward, null, 0, null, 0.0)
        val result = AppleConfiguredAdRewards(primary, listOf(entitlement, unknown)).toConfiguredAdRewards()
        assertEquals(AdRewardConfiguration.VirtualCurrency("coins", 10), result.reward)
        assertEquals(AdRewardConfiguration.Entitlement("pro", 1800.seconds), result.moreRewards[0])
        assertSame(AdRewardConfiguration.UnsupportedReward, result.moreRewards[1])
    }

    @Test
    fun `preserves resolved absence`() {
        assertSame(
            ConfiguredAdRewards.NO_CONFIGURED_REWARD,
            AppleConfiguredAdRewards(null, emptyList<AppleAdRewardConfiguration>()).toConfiguredAdRewards(),
        )
    }

    @Test
    fun `unrepresentable Apple currency amount stays unsupported without wrapping`() {
        val reward = AppleAdRewardConfiguration(
            ConfiguredRewardKindVirtualCurrency, "coins", Int.MAX_VALUE.toLong() + 1, null, 0.0,
        )
        assertSame(
            AdRewardConfiguration.UnsupportedReward,
            AppleConfiguredAdRewards(reward, emptyList<AppleAdRewardConfiguration>()).toConfiguredAdRewards().reward,
        )
    }

    @Test
    fun `invalid duration stays unsupported`() {
        val reward = AppleAdRewardConfiguration(ConfiguredRewardKindEntitlement, null, 0, "pro", Double.POSITIVE_INFINITY)
        assertSame(
            AdRewardConfiguration.UnsupportedReward,
            AppleConfiguredAdRewards(reward, emptyList<AppleAdRewardConfiguration>()).toConfiguredAdRewards().reward,
        )
    }
}
