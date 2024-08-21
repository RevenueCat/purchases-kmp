@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.models.StoreProduct as RcStoreProduct

public actual class StoreProduct(private val wrapped: RcStoreProduct) : RcStoreProduct by wrapped

public actual val StoreProduct.id: String
    get() = id
public actual val StoreProduct.type: ProductType
    get() = type
public actual val StoreProduct.category: ProductCategory?
    get() = type.toProductCategoryOrNull()
public actual val StoreProduct.price: Price
    get() = price
public actual val StoreProduct.title: String
    get() = title
public actual val StoreProduct.localizedDescription: String?
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
public actual val StoreProduct.presentedOfferingContext: PresentedOfferingContext?
    get() = presentedOfferingContext
public actual fun StoreProduct.pricePerWeek(): Price? =
    pricePerWeek()

public actual fun StoreProduct.pricePerMonth(): Price? =
    pricePerMonth()

public actual fun StoreProduct.pricePerYear(): Price? =
    pricePerYear()
