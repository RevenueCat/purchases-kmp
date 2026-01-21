package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi

/**
 * Data for tracking when a user opens/clicks on an ad.
 *
 * @property networkName The name of the ad network.
 * @property mediatorName The name of the ad mediator. See [AdMediatorName] for common values.
 * @property placement The placement of the ad, if available.
 * @property adUnitId The ad unit ID.
 * @property impressionId The impression ID.
 */
@ExperimentalRevenueCatApi
public data class AdOpenedData(
    val networkName: String,
    val mediatorName: AdMediatorName,
    val placement: String?,
    val adUnitId: String,
    val impressionId: String,
)
