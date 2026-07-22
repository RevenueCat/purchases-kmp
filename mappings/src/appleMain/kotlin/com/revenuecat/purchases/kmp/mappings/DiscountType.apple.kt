package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.DiscountType
import com.revenuecat.purchases.kn.core.RCDiscountTypeIntroductory
import com.revenuecat.purchases.kn.core.RCDiscountTypePromotional
import com.revenuecat.purchases.kn.core.RCDiscountTypeWinBack
import com.revenuecat.purchases.kn.core.RCDiscountType as IosDiscountType

internal fun IosDiscountType.toDiscountType(): DiscountType =
    when(this) {
        RCDiscountTypeIntroductory -> DiscountType.INTRODUCTORY
        RCDiscountTypePromotional -> DiscountType.PROMOTIONAL
        RCDiscountTypeWinBack -> DiscountType.WINBACK
        else -> error("Unexpected RCDiscountType: $this")
    }
