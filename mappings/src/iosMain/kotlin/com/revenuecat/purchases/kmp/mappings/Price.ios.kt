package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCStoreProduct
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProductDiscount
import swiftPMImport.com.revenuecat.purchases.models.priceAmount
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
