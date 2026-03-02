package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.ProductType
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductTypeAutoRenewableSubscription
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductTypeConsumable
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductTypeNonConsumable
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductTypeNonRenewableSubscription
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductType as IosStoreProductType

internal fun IosStoreProductType.toProductType(): ProductType =
    when (this) {
        RCStoreProductTypeAutoRenewableSubscription,
        RCStoreProductTypeNonRenewableSubscription-> ProductType.SUBS
        RCStoreProductTypeConsumable,
        RCStoreProductTypeNonConsumable-> ProductType.INAPP
        else -> error("Unexpected RCStoreProductType: $this")
    }
