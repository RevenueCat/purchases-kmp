@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

public actual class StoreProductDiscount private constructor(
    public val price: Price,
    public val numberOfPeriods: Long,
    public val offerIdentifier: String?,
    public val paymentMode: DiscountPaymentMode,
    public val subscriptionPeriod: Period,
    public val type: DiscountType,
)

public actual fun StoreProductDiscount.price(parentProduct: StoreProduct): Price =
    price

public actual val StoreProductDiscount.numberOfPeriods: Long
    get() = numberOfPeriods

public actual val StoreProductDiscount.offerIdentifier: String?
    get() = offerIdentifier

public actual val StoreProductDiscount.paymentMode: DiscountPaymentMode
    get() = paymentMode

public actual val StoreProductDiscount.subscriptionPeriod: Period
    get() = subscriptionPeriod

public actual val StoreProductDiscount.type: DiscountType
    get() = type
