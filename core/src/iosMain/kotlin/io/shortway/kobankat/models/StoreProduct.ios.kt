@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.pricePerMonth
import cocoapods.PurchasesHybridCommon.pricePerWeek
import cocoapods.PurchasesHybridCommon.pricePerYear
import io.shortway.kobankat.ProductType
import io.shortway.kobankat.i18n.Locale
import io.shortway.kobankat.i18n.toNsLocale
import io.shortway.kobankat.toProductType
import platform.Foundation.NSDecimalNumber

public actual typealias StoreProduct = RCStoreProduct

public actual val StoreProduct.id: String
    get() = productIdentifier()
public actual val StoreProduct.type: ProductType
    get() = productType().toProductType()
public actual val StoreProduct.price: Price
    get() = toPrice()
public actual val StoreProduct.title: String
    get() = localizedTitle()
public actual val StoreProduct.description: String?
    get() = localizedDescription()
public actual val StoreProduct.period: Period?
    get() = subscriptionPeriod()
public actual val StoreProduct.subscriptionOptions: SubscriptionOptions?
    get() = null
public actual val StoreProduct.defaultOption: SubscriptionOption?
    get() = null
public actual val StoreProduct.discounts: List<StoreProductDiscount>
    get() = discounts().map { it as StoreProductDiscount }
public actual val StoreProduct.introductoryDiscount: StoreProductDiscount?
    get() = introductoryDiscount()
public actual val StoreProduct.purchasingData: PurchasingData
    get() = toPurchasingData()
public actual val StoreProduct.presentedOfferingIdentifier: String?
    get() = null

public actual fun StoreProduct.pricePerWeek(locale: Locale): Price? =
    pricePerWeek()?.let { buildPriceOrNull(it, locale) }

public actual fun StoreProduct.pricePerMonth(locale: Locale): Price? =
    pricePerMonth()?.let { buildPriceOrNull(it, locale) }

public actual fun StoreProduct.pricePerYear(locale: Locale): Price? =
    pricePerYear()?.let { buildPriceOrNull(it, locale) }

public actual fun StoreProduct.formattedPricePerMonth(locale: Locale): String? =
    pricePerMonth(locale)?.formatted

private fun RCStoreProduct.buildPriceOrNull(amountDecimal: NSDecimalNumber, locale: Locale): Price? =
    priceFormatter()?.run {
        setLocale(locale.toNsLocale())
        stringFromNumber(amountDecimal)
    }?.let { formatted ->
        Price(
            formatted = formatted,
            amountDecimal = amountDecimal,
            currencyCode = currencyCodeOrUsd()
        )
    }
