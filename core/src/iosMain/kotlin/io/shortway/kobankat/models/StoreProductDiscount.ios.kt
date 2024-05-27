package io.shortway.kobankat.models

import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount

public actual typealias StoreProductDiscount = RCStoreProductDiscount

public actual val StoreProductDiscount.price: Price
    get() = toPrice()

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
