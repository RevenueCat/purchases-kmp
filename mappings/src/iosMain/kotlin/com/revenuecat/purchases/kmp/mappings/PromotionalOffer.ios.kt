package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import platform.Foundation.NSNumberFormatter
import cocoapods.PurchasesHybridCommon.RCPromotionalOffer as NativeIosPromotionalOffer

public fun NativeIosPromotionalOffer.toPromotionalOffer(priceFormatter: NSNumberFormatter?): PromotionalOffer =
    IosPromotionalOffer(this, priceFormatter)

public fun PromotionalOffer.toIosPromotionalOffer(): NativeIosPromotionalOffer = (this as IosPromotionalOffer).wrapped

private class IosPromotionalOffer(val wrapped: NativeIosPromotionalOffer, priceFormatter: NSNumberFormatter?): PromotionalOffer {
    override val discount: StoreProductDiscount =
        wrapped.discount().toStoreProductDiscount(priceFormatter)
}
