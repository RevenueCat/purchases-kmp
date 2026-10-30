package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.BillingPlanType

@Suppress("unused")
private class BillingPlanTypeAPI {
    fun check(type: BillingPlanType) {
        when (type) {
            BillingPlanType.UP_FRONT,
            BillingPlanType.MONTHLY,
            BillingPlanType.UNKNOWN,
            -> {
            }
        }.exhaustive
    }
}
