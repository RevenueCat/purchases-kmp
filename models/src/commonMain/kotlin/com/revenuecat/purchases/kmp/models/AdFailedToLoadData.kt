package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi

/**
 * Data for tracking when an ad has failed to load.
 *
 * @property networkName The name of the ad network.
 * @property mediatorName The name of the ad mediator. See [AdMediatorName] for common values.
 * @property adFormat The format of the ad. See [AdFormat] for common values.
 * @property placement The placement of the ad, if available.
 * @property adUnitId The ad unit ID.
 * @property mediatorErrorCode The mediator error code.
 */
@ExperimentalRevenueCatApi
public class AdFailedToLoadData(
    public val networkName: String,
    public val mediatorName: AdMediatorName,
    public val adFormat: AdFormat,
    public val placement: String?,
    public val adUnitId: String,
    public val mediatorErrorCode: Int?,
)
