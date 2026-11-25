package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.BillingPlanType
import com.revenuecat.purchases.kn.core.RCBillingPlanType

internal fun RCBillingPlanType.toBillingPlanType(): BillingPlanType =
    when (this) {
        RCBillingPlanType.RCUpFront() -> BillingPlanType.UP_FRONT
        RCBillingPlanType.RCMonthly() -> BillingPlanType.MONTHLY
        else -> BillingPlanType.UNKNOWN
    }
