package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.toProductType
import com.revenuecat.purchases.models.StoreProduct as AndroidStoreProduct

public actual class StoreProduct(
    internal val wrapped: AndroidStoreProduct
) {
    public actual val id: String = wrapped.id
    public actual val type: ProductType = wrapped.type.toProductType()
    public actual val category: ProductCategory? = type.toProductCategoryOrNull()
    public actual val price: Price = Price(wrapped.price)
    public actual val title: String = wrapped.title
    public actual val localizedDescription: String? = wrapped.description
    public actual val period: Period? = wrapped.period?.let { Period(it) }
    public actual val subscriptionOptions: SubscriptionOptions? =
        wrapped.subscriptionOptions?.toSubscriptionOptions()
    public actual val defaultOption: SubscriptionOption? =
        wrapped.defaultOption?.let { AndroidSubscriptionOption(it) }
    public actual val discounts: List<StoreProductDiscount> = emptyList()
    public actual val introductoryDiscount: StoreProductDiscount? = null
    public actual val purchasingData: PurchasingData = AndroidPurchasingData(wrapped.purchasingData)
    public actual val presentedOfferingContext: PresentedOfferingContext? =
        wrapped.presentedOfferingContext?.let { PresentedOfferingContext(it) }
}
