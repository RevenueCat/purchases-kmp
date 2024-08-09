package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCPaymentMode
import cocoapods.PurchasesHybridCommon.RCPaymentModeFreeTrial
import cocoapods.PurchasesHybridCommon.RCPaymentModePayAsYouGo
import cocoapods.PurchasesHybridCommon.RCPaymentModePayUpFront

public actual enum class DiscountPaymentMode {
    FREE_TRIAL,
    PAY_AS_YOU_GO,
    PAY_UP_FRONT,
}

internal fun RCPaymentMode.toDiscountPaymentMode(): DiscountPaymentMode =
    when (this) {
        RCPaymentModeFreeTrial -> DiscountPaymentMode.FREE_TRIAL
        RCPaymentModePayAsYouGo -> DiscountPaymentMode.PAY_AS_YOU_GO
        RCPaymentModePayUpFront -> DiscountPaymentMode.PAY_UP_FRONT
        else -> error("Unexpected RCPaymentMode: $this")
    }
