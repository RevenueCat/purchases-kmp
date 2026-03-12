package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi

/**
 * Parameters for tracking a custom paywall impression event.
 *
 * @property paywallId An optional identifier for the custom paywall being shown.
 */
@ExperimentalRevenueCatApi
public class CustomPaywallImpressionParams(
    public val paywallId: String? = null,
)
