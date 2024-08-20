package com.revenuecat.purchases.kmp.models

import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSNumberFormatter

public actual class Price(
    localizedPrice: String,
    currencyCode: String?,
    priceFormatter: NSNumberFormatter?
) {

    public actual val formatted: String = localizedPrice

    public actual val amountMicros: Long =
        priceFormatter.amountDecimalOrDefault(
            localizedPrice = localizedPrice,
            defaultValue = "0.0"
        ).decimalNumberByMultiplyingByPowerOf10(6).longValue

    public actual val currencyCode: String =
        currencyCode ?: priceFormatter?.currencyCode() ?: "USD" // FIXME revisit

}

private fun NSNumberFormatter?.amountDecimalOrDefault(
    localizedPrice: String,
    defaultValue: String
): NSDecimalNumber =
    this?.numberFromString(localizedPrice)
        ?.let { NSDecimalNumber.decimalNumberWithString(it.stringValue) }
        ?: NSDecimalNumber.decimalNumberWithString(defaultValue)
