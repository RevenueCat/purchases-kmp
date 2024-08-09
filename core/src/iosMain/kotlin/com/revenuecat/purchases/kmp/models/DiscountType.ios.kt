package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCDiscountTypeIntroductory
import cocoapods.PurchasesHybridCommon.RCDiscountTypePromotional
import cocoapods.PurchasesHybridCommon.RCDiscountType as IosDiscountType

internal fun IosDiscountType.toDiscountType(): DiscountType =
    when(this) {
        RCDiscountTypeIntroductory -> DiscountType.INTRODUCTORY
        RCDiscountTypePromotional -> DiscountType.PROMOTIONAL
        else -> error("Unexpected RCDiscountType: $this")
    }
