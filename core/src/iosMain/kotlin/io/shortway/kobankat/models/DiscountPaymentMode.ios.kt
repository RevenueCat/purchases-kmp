package io.shortway.kobankat.models

import cocoapods.RevenueCat.RCPaymentMode
import cocoapods.RevenueCat.RCPaymentModeFreeTrial
import cocoapods.RevenueCat.RCPaymentModePayAsYouGo
import cocoapods.RevenueCat.RCPaymentModePayUpFront

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