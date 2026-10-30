package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.BillingPlanType
import com.revenuecat.purchases.kn.core.RCBillingPlanType
import kotlin.test.Test
import kotlin.test.assertEquals

class BillingPlanTypeTest {

    @Test
    fun `maps every iOS billing plan type`() {
        assertEquals(BillingPlanType.UP_FRONT, RCBillingPlanType.RCUpFront().toBillingPlanType())
        assertEquals(BillingPlanType.MONTHLY, RCBillingPlanType.RCMonthly().toBillingPlanType())
    }
}
