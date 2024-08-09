package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.BillingFeature as AndroidBillingFeature

internal fun BillingFeature.toAndroidBillingFeature(): AndroidBillingFeature =
    when (this) {
        BillingFeature.SUBSCRIPTIONS -> AndroidBillingFeature.SUBSCRIPTIONS
        BillingFeature.SUBSCRIPTIONS_UPDATE -> AndroidBillingFeature.SUBSCRIPTIONS_UPDATE
        BillingFeature.PRICE_CHANGE_CONFIRMATION -> AndroidBillingFeature.PRICE_CHANGE_CONFIRMATION
    }
