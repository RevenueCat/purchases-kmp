package com.revenuecat.purchases.kmp.models

/**
 * Parameters for tracking a custom paywall impression event.
 *
 * @param paywallId An optional identifier for the custom paywall being shown.
 * @param offeringId An optional identifier for the offering associated with the custom paywall.
 * If not provided, the SDK will use the current offering identifier from the cache.
 */
public class CustomPaywallImpressionParams(
    public val paywallId: String? = null,
    public val offeringId: String? = null,
)
