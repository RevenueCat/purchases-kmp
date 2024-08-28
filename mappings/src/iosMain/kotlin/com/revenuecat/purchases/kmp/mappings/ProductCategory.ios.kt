package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCStoreProductCategory
import cocoapods.PurchasesHybridCommon.RCStoreProductCategoryNonSubscription
import cocoapods.PurchasesHybridCommon.RCStoreProductCategorySubscription
import com.revenuecat.purchases.kmp.models.ProductCategory

internal fun RCStoreProductCategory.toProductCategory(): ProductCategory =
    when (this) {
        RCStoreProductCategorySubscription -> ProductCategory.SUBSCRIPTION
        RCStoreProductCategoryNonSubscription -> ProductCategory.NON_SUBSCRIPTION
        else -> error("Unexpected RCStoreProductCategory: $this")
    }
