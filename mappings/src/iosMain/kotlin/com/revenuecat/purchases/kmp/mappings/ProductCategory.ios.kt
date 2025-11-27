package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductCategory
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductCategoryNonSubscription
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductCategorySubscription
import com.revenuecat.purchases.kmp.models.ProductCategory

internal fun RCStoreProductCategory.toProductCategory(): ProductCategory =
    when (this) {
        RCStoreProductCategorySubscription -> ProductCategory.SUBSCRIPTION
        RCStoreProductCategoryNonSubscription -> ProductCategory.NON_SUBSCRIPTION
        else -> error("Unexpected RCStoreProductCategory: $this")
    }
