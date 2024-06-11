package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.PricingPhase
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.isBasePlan
import com.revenuecat.purchases.kmp.models.isPrepaid

@Suppress("unused", "UNUSED_VARIABLE")
private class SubscriptionOptionAPI {
    fun checkSubscriptionOption(subscriptionOption: SubscriptionOption) {
        val phases: List<PricingPhase> = subscriptionOption.pricingPhases
        val tags: List<String> = subscriptionOption.tags
        val isBasePlan: Boolean = subscriptionOption.isBasePlan
        val presentedOfferingId: String? = subscriptionOption.presentedOfferingIdentifier
        val isPrepaid: Boolean = subscriptionOption.isPrepaid
    }
}
