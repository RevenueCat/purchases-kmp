package io.shortway.kobankat.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount
import cocoapods.PurchasesHybridCommon.price
import cocoapods.PurchasesHybridCommon.priceLocale
import platform.Foundation.NSDecimalNumber
import platform.Foundation.currencyCode

public actual data class Price(
    actual val formatted: String,
    actual val amountMicros: Long,
    actual val currencyCode: String,
) {
    internal constructor(
        formatted: String,
        amountDecimal: NSDecimalNumber,
        currencyCode: String,
    ): this(
        formatted = formatted,
        amountMicros = amountDecimal.decimalNumberByMultiplyingByPowerOf10(6).longValue,
        currencyCode = currencyCode,
    )
}

internal fun RCStoreProduct.toPrice(): Price =
    Price(
        formatted = localizedPriceString(),
        amountDecimal = price(),
        currencyCode = currencyCodeOrUsd(),
    )

internal fun RCStoreProduct.currencyCodeOrUsd(): String =
    currencyCode() ?: priceLocale().currencyCode() ?: "USD"

internal fun RCStoreProductDiscount.toPrice(): Price =
    Price(
        formatted = localizedPriceString(),
        amountDecimal = price(),
        currencyCode = currencyCodeOrUsd(),
    )

internal fun RCStoreProductDiscount.currencyCodeOrUsd(): String =
    currencyCode() ?: "USD"
