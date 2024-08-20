package com.revenuecat.purchases.kmp.models

import platform.Foundation.NSNumberFormatter
import cocoapods.PurchasesHybridCommon.RCPromotionalOffer as IosPromotionalOffer

public actual class PromotionalOffer(
    internal val wrapped: IosPromotionalOffer,
    priceFormatter: NSNumberFormatter?,
) {

    public actual val discount: StoreProductDiscount =
        StoreProductDiscount(wrapped.discount(), priceFormatter)

}

