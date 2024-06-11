@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.toProductType

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
public actual val StoreProduct.presentedOfferingContext: PresentedOfferingContext?
    get() = null
