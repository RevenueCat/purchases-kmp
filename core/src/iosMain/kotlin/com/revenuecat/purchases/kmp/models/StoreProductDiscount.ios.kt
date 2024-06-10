package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount

public actual typealias StoreProductDiscount = RCStoreProductDiscount

// FIXME create non-aliased class like on Android, and remove the parentProduct parameter.
public actual fun StoreProductDiscount.price(parentProduct: StoreProduct): Price =
    toPrice(parentProduct.priceFormatter())

public actual val StoreProductDiscount.numberOfPeriods: Long
    get() = numberOfPeriods()

public actual val StoreProductDiscount.offerIdentifier: String?
    get() = offerIdentifier()

public actual val StoreProductDiscount.paymentMode: DiscountPaymentMode
    get() = paymentMode().toDiscountPaymentMode()

public actual val StoreProductDiscount.subscriptionPeriod: Period
    get() = subscriptionPeriod()

public actual val StoreProductDiscount.type: DiscountType
    get() = type().toDiscountType()
