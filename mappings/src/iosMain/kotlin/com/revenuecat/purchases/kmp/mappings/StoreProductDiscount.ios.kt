package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.DiscountPaymentMode
import com.revenuecat.purchases.kmp.models.DiscountType
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount as NativeIosStoreProductDiscount

public fun NativeIosStoreProductDiscount.toStoreProductDiscount(): StoreProductDiscount =
    IosStoreProductDiscount(this)

public fun StoreProductDiscount.toIosStoreProductDiscount(): NativeIosStoreProductDiscount = (this as IosStoreProductDiscount).wrapped

private class IosStoreProductDiscount(
    val wrapped: NativeIosStoreProductDiscount,
): StoreProductDiscount {
    override val price: Price = wrapped.toPrice()
    override val numberOfPeriods: Long = wrapped.numberOfPeriods()
    override val offerIdentifier: String? = wrapped.offerIdentifier()
    override val paymentMode: DiscountPaymentMode = wrapped.paymentMode().toDiscountPaymentMode()
    override val subscriptionPeriod: Period = wrapped.subscriptionPeriod().toPeriod()
    override val type: DiscountType = wrapped.type().toDiscountType()
}
