package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.models.ProductCategory

internal fun ProductType.toProductCategoryOrNull(): ProductCategory? =
    when (this) {
        ProductType.SUBS -> ProductCategory.SUBSCRIPTION
        ProductType.INAPP -> ProductCategory.NON_SUBSCRIPTION
        ProductType.UNKNOWN -> null
    }
