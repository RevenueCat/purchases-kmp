package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCDiscountTypeIntroductory
import cocoapods.PurchasesHybridCommon.RCDiscountTypePromotional
import cocoapods.PurchasesHybridCommon.RCDiscountTypeWinBack
import com.revenuecat.purchases.kmp.models.DiscountType
import cocoapods.PurchasesHybridCommon.RCDiscountType as IosDiscountType

internal fun IosDiscountType.toDiscountType(): DiscountType =
    when(this) {
        RCDiscountTypeIntroductory -> DiscountType.INTRODUCTORY
        RCDiscountTypePromotional -> DiscountType.PROMOTIONAL
        RCDiscountTypeWinBack -> DiscountType.WINBACK
        else -> error("Unexpected RCDiscountType: $this")
    }
