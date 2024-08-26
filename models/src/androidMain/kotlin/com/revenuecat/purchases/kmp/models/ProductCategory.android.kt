package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ProductType

internal fun ProductType.toProductCategoryOrNull(): ProductCategory? =
    when (this) {
        ProductType.SUBS -> com.revenuecat.purchases.kmp.models.ProductCategory.SUBSCRIPTION
        ProductType.INAPP -> com.revenuecat.purchases.kmp.models.ProductCategory.NON_SUBSCRIPTION
        ProductType.UNKNOWN -> null
    }
