package com.revenuecat.purchases.kmp.models

/**
 * Ties a loaded rewarded ad to its server-side reward verification.
 *
 * @property customData Set as the ad network's server-side verification custom data.
 * @property clientTransactionId Correlates the ad with its verification.
 * @property appUserID The app user the reward is attributed to; set as the ad network's SSV user identifier.
 */
public class RewardVerificationToken(
    public val customData: String,
    public val clientTransactionId: String,
    public val appUserID: String,
)
