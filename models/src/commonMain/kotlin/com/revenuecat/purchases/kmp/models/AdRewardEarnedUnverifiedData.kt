package com.revenuecat.purchases.kmp.models

/**
 * Data for the moment an ad SDK reports that the user earned a reward, before backend verification.
 *
 * @property networkName The name of the ad network, if available.
 * @property mediatorName The name of the ad mediator. See [AdMediatorName] for common values.
 * @property adFormat The format of the ad. See [AdFormat] for common values.
 * @property placement The placement of the ad, if available.
 * @property adUnitId The ad unit ID.
 * @property impressionId The impression ID.
 */
public class AdRewardEarnedUnverifiedData(
    public val networkName: String? = null,
    public val mediatorName: AdMediatorName,
    public val adFormat: AdFormat,
    public val placement: String?,
    public val adUnitId: String,
    public val impressionId: String,
)
