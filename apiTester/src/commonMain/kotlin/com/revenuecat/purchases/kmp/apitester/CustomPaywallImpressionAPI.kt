package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomPaywallImpressionParams

@Suppress("unused", "UNUSED_VARIABLE")
@OptIn(ExperimentalRevenueCatApi::class)
private class CustomPaywallImpressionAPI {
    fun check(purchases: Purchases) {
        // CustomPaywallImpressionParams constructors
        val defaultParams: CustomPaywallImpressionParams = CustomPaywallImpressionParams()
        val paramsWithId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = "my-paywall")
        val paramsWithNull: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = null)

        // CustomPaywallImpressionParams properties
        val paywallId: String? = paramsWithId.paywallId

        // trackCustomPaywallImpression API
        purchases.trackCustomPaywallImpression()
        purchases.trackCustomPaywallImpression(defaultParams)
        purchases.trackCustomPaywallImpression(paramsWithId)
    }
}
