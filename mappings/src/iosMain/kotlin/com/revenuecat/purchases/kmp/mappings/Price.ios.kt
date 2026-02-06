package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount
import cocoapods.PurchasesHybridCommon.priceAmount
import com.revenuecat.purchases.kmp.models.Price
import platform.Foundation.NSDecimalNumber

internal fun RCStoreProduct.toPrice(): Price =
    Price(
        formatted = localizedPriceString(),
        amountMicros = priceAmount().decimalNumberByMultiplyingByPowerOf10(6).longValue,
        currencyCode = currencyCodeOrUsd(),
    )

internal fun RCStoreProduct.currencyCodeOrUsd(): String =
    currencyCode() ?: priceFormatter()?.currencyCode() ?: "USD" // FIXME revisit

internal fun RCStoreProductDiscount.toPrice(): Price =
    Price(
        formatted = localizedPriceString(),
        amountMicros = priceAmount().decimalNumberByMultiplyingByPowerOf10(6).longValue,
        currencyCode = currencyCodeOrUsd(),
    )

internal fun RCStoreProductDiscount.currencyCodeOrUsd(): String =
    currencyCode() ?: "USD" // FIXME revisit


/**
 * Returns a non-null Price only if all provided arguments are non-null.
 */
internal fun priceOrNull(
    currencyCode: String,
    formatted: String?,
    amountDecimal: NSDecimalNumber?
): Price? = priceOrNull(
    currencyCode = currencyCode,
    formatted = formatted,
    amountMicros = amountDecimal?.decimalNumberByMultiplyingByPowerOf10(6)?.longValue
)

/**
 * Returns a non-null Price only if all provided arguments are non-null.
 */
internal fun priceOrNull(
    currencyCode: String,
    formatted: String?,
    amountMicros: Long?
): Price? =
    formatted?.let {
        amountMicros?.let {
            Price(
                formatted = formatted,
                amountMicros = amountMicros,
                currencyCode = currencyCode,
            )
        }
    }
