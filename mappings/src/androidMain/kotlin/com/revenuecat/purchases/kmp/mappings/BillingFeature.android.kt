package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.BillingFeature
import com.revenuecat.purchases.models.BillingFeature as AndroidBillingFeature

public fun BillingFeature.toAndroidBillingFeature(): AndroidBillingFeature =
    when (this) {
        BillingFeature.SUBSCRIPTIONS -> AndroidBillingFeature.SUBSCRIPTIONS
        BillingFeature.SUBSCRIPTIONS_UPDATE -> AndroidBillingFeature.SUBSCRIPTIONS_UPDATE
        BillingFeature.PRICE_CHANGE_CONFIRMATION -> AndroidBillingFeature.PRICE_CHANGE_CONFIRMATION
    }
