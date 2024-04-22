package io.shortway.kobankat.apitester

import io.shortway.kobankat.models.PricingPhase
import io.shortway.kobankat.models.SubscriptionOption
import io.shortway.kobankat.models.isBasePlan
import io.shortway.kobankat.models.isPrepaid

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
