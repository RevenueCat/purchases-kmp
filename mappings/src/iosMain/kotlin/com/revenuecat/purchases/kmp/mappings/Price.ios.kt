package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount
import cocoapods.PurchasesHybridCommon.priceAmount
import com.revenuecat.purchases.kmp.models.Price

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
