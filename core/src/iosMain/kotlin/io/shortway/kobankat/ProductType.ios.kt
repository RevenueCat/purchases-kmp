package io.shortway.kobankat

import cocoapods.RevenueCat.RCStoreProductType
import cocoapods.RevenueCat.RCStoreProductTypeAutoRenewableSubscription
import cocoapods.RevenueCat.RCStoreProductTypeConsumable
import cocoapods.RevenueCat.RCStoreProductTypeNonConsumable
import cocoapods.RevenueCat.RCStoreProductTypeNonRenewableSubscription

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

