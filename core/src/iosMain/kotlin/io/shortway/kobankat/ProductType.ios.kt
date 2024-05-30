package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCStoreProductType
import cocoapods.PurchasesHybridCommon.RCStoreProductTypeAutoRenewableSubscription
import cocoapods.PurchasesHybridCommon.RCStoreProductTypeConsumable
import cocoapods.PurchasesHybridCommon.RCStoreProductTypeNonConsumable
import cocoapods.PurchasesHybridCommon.RCStoreProductTypeNonRenewableSubscription

public actual enum class ProductType {
    SUBS,
    INAPP,
    UNKNOWN,
}

internal fun RCStoreProductType.toProductType(): ProductType =
    when (this) {
        RCStoreProductTypeAutoRenewableSubscription,
        RCStoreProductTypeNonRenewableSubscription-> ProductType.SUBS
        RCStoreProductTypeConsumable,
        RCStoreProductTypeNonConsumable-> ProductType.INAPP
        else -> error("Unexpected RCStoreProductType: $this")
    }
