package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.Price as AndroidPrice

public actual class Price(
    internal val wrapped: AndroidPrice
) {
    public actual val formatted: String = wrapped.formatted
    public actual val amountMicros: Long = wrapped.amountMicros
    public actual val currencyCode: String = wrapped.currencyCode
}
