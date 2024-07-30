@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.pricePerMonthAmount
import cocoapods.PurchasesHybridCommon.pricePerMonthString
import cocoapods.PurchasesHybridCommon.pricePerWeekAmount
import cocoapods.PurchasesHybridCommon.pricePerWeekString
import cocoapods.PurchasesHybridCommon.pricePerYearAmount
import cocoapods.PurchasesHybridCommon.pricePerYearString
import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.toProductType

public actual typealias StoreProduct = RCStoreProduct

public actual val StoreProduct.id: String
    get() = productIdentifier()
public actual val StoreProduct.type: ProductType
    get() = productType().toProductType()
public actual val StoreProduct.category: ProductCategory?
    get() = productCategory().toProductCategory()
public actual val StoreProduct.price: Price
    get() = toPrice()
public actual val StoreProduct.title: String
    get() = localizedTitle()
public actual val StoreProduct.localizedDescription: String?
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
public actual val StoreProduct.presentedOfferingContext: PresentedOfferingContext?
    get() = null
public actual fun StoreProduct.pricePerWeek(): Price? =
    priceOrNull(
        currencyCode = currencyCodeOrUsd(),
        formatted = pricePerWeekString(),
        amountDecimal = pricePerWeekAmount(),
    )

public actual fun StoreProduct.pricePerMonth(): Price? =
    priceOrNull(
        currencyCode = currencyCodeOrUsd(),
        formatted = pricePerMonthString(),
        amountDecimal = pricePerMonthAmount(),
    )

public actual fun StoreProduct.pricePerYear(): Price? =
    priceOrNull(
        currencyCode = currencyCodeOrUsd(),
        formatted = pricePerYearString(),
        amountDecimal = pricePerYearAmount(),
    )
