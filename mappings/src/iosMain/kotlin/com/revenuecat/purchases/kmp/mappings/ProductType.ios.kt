package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductTypeAutoRenewableSubscription
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductTypeConsumable
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductTypeNonConsumable
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductTypeNonRenewableSubscription
import com.revenuecat.purchases.kmp.models.ProductType
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductType as IosStoreProductType

internal fun IosStoreProductType.toProductType(): ProductType =
    when (this) {
        RCStoreProductTypeAutoRenewableSubscription,
        RCStoreProductTypeNonRenewableSubscription-> ProductType.SUBS
        RCStoreProductTypeConsumable,
        RCStoreProductTypeNonConsumable-> ProductType.INAPP
        else -> error("Unexpected RCStoreProductType: $this")
    }
