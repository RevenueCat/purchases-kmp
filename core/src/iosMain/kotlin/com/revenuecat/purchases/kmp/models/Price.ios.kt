package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSNumberFormatter

public actual data class Price(
    actual val formatted: String,
    actual val amountMicros: Long,
    actual val currencyCode: String,
) {
    internal constructor(
        formatted: String,
        amountDecimal: NSDecimalNumber,
        currencyCode: String,
    ) : this(
        formatted = formatted,
        amountMicros = amountDecimal.decimalNumberByMultiplyingByPowerOf10(6).longValue,
        currencyCode = currencyCode,
    )
}

internal fun RCStoreProduct.toPrice(): Price =
    localizedPriceString().let { localizedPrice ->
        Price(
            formatted = localizedPrice,
            amountDecimal = priceFormatter().amountDecimalOrDefault(
                localizedPrice = localizedPrice,
                defaultValue = "0.0"
            ),
            currencyCode = currencyCodeOrUsd(),
        )
    }

internal fun RCStoreProduct.currencyCodeOrUsd(): String =
    currencyCode() ?: priceFormatter()?.currencyCode() ?: "USD" // FIXME revisit

internal fun RCStoreProductDiscount.toPrice(formatter: NSNumberFormatter?): Price =
    localizedPriceString().let { localizedPrice ->
        Price(
            formatted = localizedPrice,
            amountDecimal = formatter.amountDecimalOrDefault(
                localizedPrice = localizedPrice,
                defaultValue = "0.0"
            ),
            currencyCode = currencyCodeOrUsd(),
        )
    }


internal fun RCStoreProductDiscount.currencyCodeOrUsd(): String =
    currencyCode() ?: "USD" // FIXME revisit

private fun NSNumberFormatter?.amountDecimalOrDefault(localizedPrice: String, defaultValue: String): NSDecimalNumber =
    this?.numberFromString(localizedPrice)
        ?.let { NSDecimalNumber.decimalNumberWithString(it.stringValue) }
        ?: NSDecimalNumber.decimalNumberWithString(defaultValue)
