package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCDiscountType
import cocoapods.PurchasesHybridCommon.RCDiscountTypeIntroductory
import cocoapods.PurchasesHybridCommon.RCDiscountTypePromotional

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
