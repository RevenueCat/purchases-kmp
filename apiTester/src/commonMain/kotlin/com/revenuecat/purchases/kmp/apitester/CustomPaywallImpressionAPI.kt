package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.Purchases

@Suppress("unused", "UNUSED_VARIABLE")
private class CustomPaywallImpressionAPI {
    fun check(purchases: Purchases) {
        // trackCustomPaywallImpression API
        purchases.trackCustomPaywallImpression()
        purchases.trackCustomPaywallImpression(paywallId = null)
        purchases.trackCustomPaywallImpression(paywallId = "my-paywall")
    }
}
