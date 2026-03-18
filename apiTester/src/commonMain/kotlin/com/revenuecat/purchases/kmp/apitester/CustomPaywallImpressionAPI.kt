package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomPaywallImpressionParams

@Suppress("unused", "UNUSED_VARIABLE")
private class CustomPaywallImpressionAPI {
    fun check(purchases: Purchases) {
        // CustomPaywallImpressionParams constructors
        val defaultParams: CustomPaywallImpressionParams = CustomPaywallImpressionParams()
        val paramsWithId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = "my-paywall")
        val paramsWithNull: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = null)
        val paramsWithOfferingId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(offeringId = "my-offering")
        val paramsWithBoth: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = "my-paywall", offeringId = "my-offering")

        // CustomPaywallImpressionParams properties
        val paywallId: String? = paramsWithId.paywallId
        val offeringId: String? = paramsWithOfferingId.offeringId

        // trackCustomPaywallImpression API
        purchases.trackCustomPaywallImpression()
        purchases.trackCustomPaywallImpression(defaultParams)
        purchases.trackCustomPaywallImpression(paramsWithId)
        purchases.trackCustomPaywallImpression(paramsWithOfferingId)
        purchases.trackCustomPaywallImpression(paramsWithBoth)
    }
}
