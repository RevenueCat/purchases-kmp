package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalRevenueCatApi::class)
class ConfiguredAdRewardsTest {
    @Test
    fun `resolved absence has no primary or additional rewards`() {
        val rewards = ConfiguredAdRewards.NO_CONFIGURED_REWARD
        assertNull(rewards.reward)
        assertEquals(emptyList(), rewards.moreRewards)
    }

    @Test
    fun `additional rewards require a primary reward`() {
        assertFailsWith<IllegalArgumentException> {
            ConfiguredAdRewards(null, listOf(AdRewardConfiguration.UnsupportedReward))
        }
    }

    @Test
    fun `reward order is preserved independently of the input list`() {
        val primary = AdRewardConfiguration.VirtualCurrency("coins", 10)
        val additional = mutableListOf<AdRewardConfiguration>(
            AdRewardConfiguration.Entitlement("pro", 30.minutes),
            AdRewardConfiguration.UnsupportedReward,
        )
        val rewards = ConfiguredAdRewards(primary, additional)
        additional.clear()
        assertSame(primary, rewards.reward)
        assertEquals(AdRewardConfiguration.Entitlement("pro", 1800.seconds), rewards.moreRewards[0])
        assertSame(AdRewardConfiguration.UnsupportedReward, rewards.moreRewards[1])
    }

    @Test
    fun `duration composes with Kotlin time and exposes seconds`() {
        val reward = AdRewardConfiguration.Entitlement("pro", 30.minutes)
        assertEquals(30.minutes, reward.duration)
        assertEquals(1800L, reward.durationInSeconds)
    }

    @Test
    fun `invalid modeled rewards cannot be constructed`() {
        listOf(0, -1).forEach { amount ->
            assertFailsWith<IllegalArgumentException> { AdRewardConfiguration.VirtualCurrency("coins", amount) }
        }
        assertFailsWith<IllegalArgumentException> { AdRewardConfiguration.VirtualCurrency("", 1) }
        listOf(Duration.ZERO, (-1).seconds, Duration.INFINITE).forEach { duration ->
            assertFailsWith<IllegalArgumentException> { AdRewardConfiguration.Entitlement("pro", duration) }
        }
        assertFailsWith<IllegalArgumentException> { AdRewardConfiguration.Entitlement("", 1.seconds) }
    }
}
