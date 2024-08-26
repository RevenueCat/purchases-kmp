package com.revenuecat.purchases.kmp.models

public actual class StoreProductDiscount private constructor(
    public actual val price: Price,
    public actual val numberOfPeriods: Long,
    public actual val offerIdentifier: String?,
    public actual val paymentMode: DiscountPaymentMode,
    public actual val subscriptionPeriod: Period,
    public actual val type: DiscountType,
)
