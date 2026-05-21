package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.DiscountPaymentMode
import com.revenuecat.purchases.kn.core.RCPaymentModeFreeTrial
import com.revenuecat.purchases.kn.core.RCPaymentModePayAsYouGo
import com.revenuecat.purchases.kn.core.RCPaymentModePayUpFront
import com.revenuecat.purchases.kn.core.RCPaymentMode as IosDiscountPaymentMode

internal fun IosDiscountPaymentMode.toDiscountPaymentMode(): DiscountPaymentMode =
    when (this) {
        RCPaymentModeFreeTrial -> DiscountPaymentMode.FREE_TRIAL
        RCPaymentModePayAsYouGo -> DiscountPaymentMode.PAY_AS_YOU_GO
        RCPaymentModePayUpFront -> DiscountPaymentMode.PAY_UP_FRONT
        else -> error("Unexpected RCPaymentMode: $this")
    }
