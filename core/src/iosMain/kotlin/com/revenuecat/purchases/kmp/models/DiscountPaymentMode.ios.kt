package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCPaymentModeFreeTrial
import cocoapods.PurchasesHybridCommon.RCPaymentModePayAsYouGo
import cocoapods.PurchasesHybridCommon.RCPaymentModePayUpFront
import cocoapods.PurchasesHybridCommon.RCPaymentMode as IosDiscountPaymentMode

internal fun IosDiscountPaymentMode.toDiscountPaymentMode(): DiscountPaymentMode =
    when (this) {
        RCPaymentModeFreeTrial -> DiscountPaymentMode.FREE_TRIAL
        RCPaymentModePayAsYouGo -> DiscountPaymentMode.PAY_AS_YOU_GO
        RCPaymentModePayUpFront -> DiscountPaymentMode.PAY_UP_FRONT
        else -> error("Unexpected RCPaymentMode: $this")
    }
