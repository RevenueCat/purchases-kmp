package com.revenuecat.purchases.kmp.models

/**
 * Parameters for tracking a custom paywall impression event.
 *
 * @param paywallId An optional identifier for the custom paywall being shown.
 */
public class CustomPaywallImpressionParams(
    public val paywallId: String? = null,
)
