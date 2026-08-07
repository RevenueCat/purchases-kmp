package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi

/**
 * Result delivered after reward verification polling for a presented rewarded ad.
 *
 * @property verifiedReward The primary reward when verification succeeded, null otherwise.
 * @property moreRewards Additional rewards granted alongside [verifiedReward]; does not repeat it. Empty when
 * verification failed.
 * @property failed Whether verification did not complete successfully (rejected, timeout, network, etc.).
 */
@ExperimentalRevenueCatApi
public class RewardVerificationResult(
    public val verifiedReward: VerifiedReward?,
    public val moreRewards: List<VerifiedReward>,
    public val failed: Boolean,
)
