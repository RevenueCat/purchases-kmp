package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.DiscountType
import swiftPMImport.com.revenuecat.purchases.kn.core.RCDiscountTypeIntroductory
import swiftPMImport.com.revenuecat.purchases.kn.core.RCDiscountTypePromotional
import swiftPMImport.com.revenuecat.purchases.kn.core.RCDiscountTypeWinBack
import swiftPMImport.com.revenuecat.purchases.kn.core.RCDiscountType as IosDiscountType

internal fun IosDiscountType.toDiscountType(): DiscountType =
    when(this) {
        RCDiscountTypeIntroductory -> DiscountType.INTRODUCTORY
        RCDiscountTypePromotional -> DiscountType.PROMOTIONAL
        RCDiscountTypeWinBack -> DiscountType.WINBACK
        else -> error("Unexpected RCDiscountType: $this")
    }
