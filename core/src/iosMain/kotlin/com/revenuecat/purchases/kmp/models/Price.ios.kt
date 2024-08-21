package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSNumberFormatter

internal fun RCStoreProduct.toPrice(): Price =
    localizedPriceString().let { localizedPrice ->
        Price(
            formatted = localizedPrice,
            amountMicros = priceFormatter().amountDecimalOrDefault(
                localizedPrice = localizedPrice,
                defaultValue = "0.0"
            ).decimalNumberByMultiplyingByPowerOf10(6).longValue,
            currencyCode = currencyCodeOrUsd(),
        )
    }

internal fun RCStoreProduct.currencyCodeOrUsd(): String =
    currencyCode() ?: priceFormatter()?.currencyCode() ?: "USD" // FIXME revisit

internal fun RCStoreProductDiscount.toPrice(formatter: NSNumberFormatter?): Price =
    localizedPriceString().let { localizedPrice ->
        Price(
            formatted = localizedPrice,
            amountMicros = formatter.amountDecimalOrDefault(
                localizedPrice = localizedPrice,
                defaultValue = "0.0"
            ).decimalNumberByMultiplyingByPowerOf10(6).longValue,
            currencyCode = currencyCodeOrUsd(),
        )
    }

internal fun RCStoreProductDiscount.currencyCodeOrUsd(): String =
    currencyCode() ?: "USD" // FIXME revisit

private fun NSNumberFormatter?.amountDecimalOrDefault(
    localizedPrice: String,
    defaultValue: String
): NSDecimalNumber =
    this?.numberFromString(localizedPrice)
        ?.let { NSDecimalNumber.decimalNumberWithString(it.stringValue) }
        ?: NSDecimalNumber.decimalNumberWithString(defaultValue)
