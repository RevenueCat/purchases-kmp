package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCStoreProductTypeAutoRenewableSubscription
import cocoapods.PurchasesHybridCommon.RCStoreProductTypeConsumable
import cocoapods.PurchasesHybridCommon.RCStoreProductTypeNonConsumable
import cocoapods.PurchasesHybridCommon.RCStoreProductTypeNonRenewableSubscription
import cocoapods.PurchasesHybridCommon.RCStoreProductType as IosStoreProductType

internal fun IosStoreProductType.toProductType(): ProductType =
    when (this) {
        RCStoreProductTypeAutoRenewableSubscription,
        RCStoreProductTypeNonRenewableSubscription-> ProductType.SUBS
        RCStoreProductTypeConsumable,
        RCStoreProductTypeNonConsumable-> ProductType.INAPP
        else -> error("Unexpected RCStoreProductType: $this")
    }
