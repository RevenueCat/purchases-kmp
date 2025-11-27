package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCDiscountTypeIntroductory
import swiftPMImport.com.revenuecat.purchases.models.RCDiscountTypePromotional
import swiftPMImport.com.revenuecat.purchases.models.RCDiscountTypeWinBack
import com.revenuecat.purchases.kmp.models.DiscountType
import swiftPMImport.com.revenuecat.purchases.models.RCDiscountType as IosDiscountType

internal fun IosDiscountType.toDiscountType(): DiscountType =
    when(this) {
        RCDiscountTypeIntroductory -> DiscountType.INTRODUCTORY
        RCDiscountTypePromotional -> DiscountType.PROMOTIONAL
        RCDiscountTypeWinBack -> DiscountType.WINBACK
        else -> error("Unexpected RCDiscountType: $this")
    }
