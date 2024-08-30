package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCDiscountTypeIntroductory
import cocoapods.PurchasesHybridCommon.RCDiscountTypePromotional
import com.revenuecat.purchases.kmp.models.DiscountType
import cocoapods.PurchasesHybridCommon.RCDiscountType as IosDiscountType

internal fun IosDiscountType.toDiscountType(): DiscountType =
    when(this) {
        RCDiscountTypeIntroductory -> DiscountType.INTRODUCTORY
        RCDiscountTypePromotional -> DiscountType.PROMOTIONAL
        else -> error("Unexpected RCDiscountType: $this")
    }
