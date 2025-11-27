package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProduct
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreProductDiscount
import swiftPMImport.com.revenuecat.purchases.kn.core.priceAmount
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
