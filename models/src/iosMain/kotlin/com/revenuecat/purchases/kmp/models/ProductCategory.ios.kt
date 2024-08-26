package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProductCategory
import cocoapods.PurchasesHybridCommon.RCStoreProductCategoryNonSubscription
import cocoapods.PurchasesHybridCommon.RCStoreProductCategorySubscription

internal fun RCStoreProductCategory.toProductCategory(): ProductCategory =
    when (this) {
        RCStoreProductCategorySubscription -> ProductCategory.SUBSCRIPTION
        RCStoreProductCategoryNonSubscription -> ProductCategory.NON_SUBSCRIPTION
        else -> error("Unexpected RCStoreProductCategory: $this")
    }
