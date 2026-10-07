package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.ProductType
import com.revenuecat.purchases.kn.core.RCStoreProductTypeAutoRenewableSubscription
import com.revenuecat.purchases.kn.core.RCStoreProductTypeConsumable
import com.revenuecat.purchases.kn.core.RCStoreProductTypeNonConsumable
import com.revenuecat.purchases.kn.core.RCStoreProductTypeNonRenewableSubscription
import com.revenuecat.purchases.kn.core.RCStoreProductType as IosStoreProductType

internal fun IosStoreProductType.toProductType(): ProductType =
    when (this) {
        RCStoreProductTypeAutoRenewableSubscription,
        RCStoreProductTypeNonRenewableSubscription-> ProductType.SUBS
        RCStoreProductTypeConsumable,
        RCStoreProductTypeNonConsumable-> ProductType.INAPP
        else -> error("Unexpected RCStoreProductType: $this")
    }
