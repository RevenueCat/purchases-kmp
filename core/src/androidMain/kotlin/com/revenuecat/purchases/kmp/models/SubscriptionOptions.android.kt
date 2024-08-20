package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.SubscriptionOptions as AndroidSubscriptionOptions

internal fun AndroidSubscriptionOptions.toSubscriptionOptions(): SubscriptionOptions =
    SubscriptionOptions(
        getBasePlan = { basePlan?.let { AndroidSubscriptionOption(it) } },
        getFreeTrial = { freeTrial?.let { AndroidSubscriptionOption(it) } },
        getIntroOffer = { introOffer?.let { AndroidSubscriptionOption(it) } },
        getDefaultOffer = { defaultOffer?.let { AndroidSubscriptionOption(it) } },
        withTag = { tag -> withTag(tag).map { AndroidSubscriptionOption(it) } },
    )