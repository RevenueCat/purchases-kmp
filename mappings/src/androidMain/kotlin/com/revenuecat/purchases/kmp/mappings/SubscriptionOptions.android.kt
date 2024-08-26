package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.SubscriptionOptions
import com.revenuecat.purchases.models.SubscriptionOptions as AndroidSubscriptionOptions

internal fun AndroidSubscriptionOptions.toSubscriptionOptions(): SubscriptionOptions =
    SubscriptionOptions(
        basePlan = basePlan?.let { AndroidSubscriptionOption(it) },
        freeTrial = freeTrial?.let { AndroidSubscriptionOption(it) },
        introOffer = introOffer?.let { AndroidSubscriptionOption(it) },
        defaultOffer = defaultOffer?.let { AndroidSubscriptionOption(it) },
        subscriptionOptions = this.map { AndroidSubscriptionOption(it) },
    )
