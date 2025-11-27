package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductCategory
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductCategoryNonSubscription
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductCategorySubscription
import com.revenuecat.purchases.kmp.models.ProductCategory

internal fun RCStoreProductCategory.toProductCategory(): ProductCategory =
    when (this) {
        RCStoreProductCategorySubscription -> ProductCategory.SUBSCRIPTION
        RCStoreProductCategoryNonSubscription -> ProductCategory.NON_SUBSCRIPTION
        else -> error("Unexpected RCStoreProductCategory: $this")
    }
