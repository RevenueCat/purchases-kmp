package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ProductType

internal fun ProductType.toProductCategoryOrNull(): ProductCategory? =
    when (this) {
        ProductType.SUBS -> ProductCategory.SUBSCRIPTION
        ProductType.INAPP -> ProductCategory.NON_SUBSCRIPTION
        ProductType.UNKNOWN -> null
    }
