package io.shortway.kobankat.models

import cocoapods.RevenueCat.RCDiscountType
import cocoapods.RevenueCat.RCDiscountTypeIntroductory
import cocoapods.RevenueCat.RCDiscountTypePromotional

public actual enum class DiscountType {
    INTRODUCTORY,
    PROMOTIONAL,
}

internal fun RCDiscountType.toDiscountType(): DiscountType =
    when(this) {
        RCDiscountTypeIntroductory -> DiscountType.INTRODUCTORY
        RCDiscountTypePromotional -> DiscountType.PROMOTIONAL
        else -> error("Unexpected RCDiscountType: $this")
    }
