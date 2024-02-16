@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat.models

import io.shortway.kobankat.ProductType
import io.shortway.kobankat.i18n.Locale
import io.shortway.kobankat.i18n.toJvmLocale
import com.revenuecat.purchases.models.StoreProduct as RcStoreProduct

public actual class StoreProduct(private val wrapped: RcStoreProduct) : RcStoreProduct by wrapped

public actual val StoreProduct.id: String
    get() = id
public actual val StoreProduct.type: ProductType
    get() = type
public actual val StoreProduct.price: Price
    get() = price
public actual val StoreProduct.title: String
    get() = title
public actual val StoreProduct.description: String?
    get() = description
public actual val StoreProduct.period: Period?
    get() = period
public actual val StoreProduct.subscriptionOptions: SubscriptionOptions?
    get() = subscriptionOptions
public actual val StoreProduct.defaultOption: SubscriptionOption?
    get() = defaultOption
public actual val StoreProduct.discounts: List<StoreProductDiscount>
    get() = emptyList()
public actual val StoreProduct.introductoryDiscount: StoreProductDiscount?
    get() = null
public actual val StoreProduct.purchasingData: PurchasingData
    get() = purchasingData
public actual val StoreProduct.presentedOfferingIdentifier: String?
    get() = presentedOfferingIdentifier

public actual fun StoreProduct.pricePerWeek(locale: Locale): Price? =
    pricePerWeek(locale.toJvmLocale())

public actual fun StoreProduct.pricePerMonth(locale: Locale): Price? =
    pricePerMonth(locale.toJvmLocale())

public actual fun StoreProduct.pricePerYear(locale: Locale): Price? =
    pricePerYear(locale.toJvmLocale())

public actual fun StoreProduct.formattedPricePerMonth(locale: Locale): String? =
    formattedPricePerMonth(locale.toJvmLocale())