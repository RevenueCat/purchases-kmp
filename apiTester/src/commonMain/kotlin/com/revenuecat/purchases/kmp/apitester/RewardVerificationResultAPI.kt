package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.RewardVerificationResult
import com.revenuecat.purchases.kmp.models.VerifiedReward

@Suppress("unused", "UNUSED_VARIABLE")
private class RewardVerificationResultAPI {
    fun check(result: RewardVerificationResult) {
        val verifiedReward: VerifiedReward? = result.verifiedReward
        val moreRewards: List<VerifiedReward> = result.moreRewards
        val failed: Boolean = result.failed
    }
}
