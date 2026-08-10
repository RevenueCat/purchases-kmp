package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.VerifiedReward
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Suppress("unused", "UNUSED_VARIABLE")
private class VerifiedRewardAPI {
    @OptIn(ExperimentalRevenueCatApi::class, ExperimentalTime::class)
    fun check(reward: VerifiedReward) {
        when (reward) {
            is VerifiedReward.VirtualCurrency -> {
                val code: String = reward.code
                val amount: Int = reward.amount
            }
            is VerifiedReward.Entitlement -> {
                val identifier: String = reward.identifier
                val expiresAtMillis: Long = reward.expiresAtMillis
                val expiresAt: Instant = reward.expiresAt
            }
            is VerifiedReward.NoReward -> { }
            is VerifiedReward.UnsupportedReward -> { }
        }.exhaustive
    }
}
