package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.models.SubscriptionOptions

@Suppress("unused", "UNUSED_VARIABLE")
private class SubscriptionOptionsAPI {
    fun checkSubscriptionOptions(
        subscriptionOptions: SubscriptionOptions
    ) {
        val freeTrial = subscriptionOptions.freeTrial
        val introOffer = subscriptionOptions.introOffer
        val tagOptions = subscriptionOptions.withTag("pick-this-one")
    }
}
