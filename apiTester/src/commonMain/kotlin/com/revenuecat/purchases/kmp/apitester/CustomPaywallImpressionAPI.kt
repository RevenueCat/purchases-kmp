package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.CustomPaywallImpressionParams
import com.revenuecat.purchases.kmp.models.Offering

@Suppress("unused", "UNUSED_VARIABLE", "DEPRECATION")
private class CustomPaywallImpressionAPI {
    fun check() {
        // CustomPaywallImpressionParams constructors
        val defaultParams: CustomPaywallImpressionParams = CustomPaywallImpressionParams()
        val paramsWithId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = "my-paywall")
        val paramsWithNullPaywallId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = null)
        val paramsWithNullOfferingId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(offeringId = null)
        val paramsWithOfferingId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(offeringId = "my-offering")
        val paramsWithBoth: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(paywallId = "my-paywall", offeringId = "my-offering")

        // CustomPaywallImpressionParams properties
        val paywallId: String? = paramsWithId.paywallId
        val offeringId: String? = paramsWithOfferingId.offeringId
    }

    fun checkWithOffering(offering: Offering) {
        // CustomPaywallImpressionParams constructors
        val paramsWithOffering: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(offering = offering)
        val paramsWithOfferingAndLegacyId: CustomPaywallImpressionParams =
            CustomPaywallImpressionParams(
                paywallId = "my-paywall",
                offering = offering,
                offeringId = "legacy-offering",
            )

        // CustomPaywallImpressionParams properties
        val offeringProperty: Offering? = paramsWithOffering.offering
    }
}
