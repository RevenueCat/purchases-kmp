package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.ProductCategory
import com.revenuecat.purchases.kmp.models.ProductType

internal fun ProductType.toProductCategoryOrNull(): ProductCategory? =
    when (this) {
        ProductType.SUBS -> ProductCategory.SUBSCRIPTION
        ProductType.INAPP -> ProductCategory.NON_SUBSCRIPTION
        ProductType.UNKNOWN -> null
    }
