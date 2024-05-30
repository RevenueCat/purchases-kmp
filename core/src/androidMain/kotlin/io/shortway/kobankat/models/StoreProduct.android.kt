@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat.models

import io.shortway.kobankat.PresentedOfferingContext
import io.shortway.kobankat.ProductType
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
public actual val StoreProduct.presentedOfferingContext: PresentedOfferingContext?
    get() = presentedOfferingContext
