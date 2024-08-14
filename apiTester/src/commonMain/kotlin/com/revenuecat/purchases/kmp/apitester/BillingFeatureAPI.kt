package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.BillingFeature

@Suppress("unused")
private class BillingFeatureAPI {

    fun check(billingFeature: BillingFeature) {
        when (billingFeature) {
            BillingFeature.SUBSCRIPTIONS,
            BillingFeature.SUBSCRIPTIONS_UPDATE,
            BillingFeature.PRICE_CHANGE_CONFIRMATION,
            -> {
            }
        }.exhaustive
    }
}
