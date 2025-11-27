package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import swiftPMImport.com.revenuecat.purchases.models.RCPromotionalOffer as NativeIosPromotionalOffer

public fun NativeIosPromotionalOffer.toPromotionalOffer(): PromotionalOffer =
    IosPromotionalOffer(this)

public fun PromotionalOffer.toIosPromotionalOffer(): NativeIosPromotionalOffer = (this as IosPromotionalOffer).wrapped

private class IosPromotionalOffer(val wrapped: NativeIosPromotionalOffer) : PromotionalOffer {
    override val discount: StoreProductDiscount =
        wrapped.discount().toStoreProductDiscount()
}
