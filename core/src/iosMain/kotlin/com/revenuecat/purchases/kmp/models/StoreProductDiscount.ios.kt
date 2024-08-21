package com.revenuecat.purchases.kmp.models

import platform.Foundation.NSNumberFormatter
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount as IosStoreProductDiscount

public actual class StoreProductDiscount(
    internal val wrapped: IosStoreProductDiscount,
    priceFormatter: NSNumberFormatter?,
) {
    public actual val price: Price = wrapped.toPrice(priceFormatter)
    public actual val numberOfPeriods: Long = wrapped.numberOfPeriods()
    public actual val offerIdentifier: String? = wrapped.offerIdentifier()
    public actual val paymentMode: DiscountPaymentMode = wrapped.paymentMode().toDiscountPaymentMode()
    public actual val subscriptionPeriod: Period = wrapped.subscriptionPeriod().toPeriod()
    public actual val type: DiscountType = wrapped.type().toDiscountType()
}
