package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCPaymentModeFreeTrial
import swiftPMImport.com.revenuecat.purchases.models.RCPaymentModePayAsYouGo
import swiftPMImport.com.revenuecat.purchases.models.RCPaymentModePayUpFront
import com.revenuecat.purchases.kmp.models.DiscountPaymentMode
import swiftPMImport.com.revenuecat.purchases.models.RCPaymentMode as IosDiscountPaymentMode

internal fun IosDiscountPaymentMode.toDiscountPaymentMode(): DiscountPaymentMode =
    when (this) {
        RCPaymentModeFreeTrial -> DiscountPaymentMode.FREE_TRIAL
        RCPaymentModePayAsYouGo -> DiscountPaymentMode.PAY_AS_YOU_GO
        RCPaymentModePayUpFront -> DiscountPaymentMode.PAY_UP_FRONT
        else -> error("Unexpected RCPaymentMode: $this")
    }
