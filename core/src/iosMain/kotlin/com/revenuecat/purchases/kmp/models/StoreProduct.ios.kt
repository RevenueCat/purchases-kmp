package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.toProductType
import cocoapods.PurchasesHybridCommon.RCStoreProduct as IosStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount as IosStoreProductDiscount

public actual class StoreProduct(
    internal val wrapped: IosStoreProduct
) {
    public actual val id: String = wrapped.productIdentifier()
    public actual val type: ProductType = wrapped.productType().toProductType()
    public actual val category: ProductCategory? = wrapped.productCategory().toProductCategory()
    public actual val price: Price =
        Price(wrapped.localizedPriceString(), wrapped.currencyCode(), wrapped.priceFormatter())
    public actual val title: String = wrapped.localizedTitle()
    public actual val localizedDescription: String? = wrapped.localizedDescription()
    public actual val period: Period? = wrapped.subscriptionPeriod()?.let { Period(it) }
    public actual val subscriptionOptions: SubscriptionOptions? = null
    public actual val defaultOption: SubscriptionOption? = null
    public actual val discounts: List<StoreProductDiscount> =
        wrapped.discounts().map {
            StoreProductDiscount(it as IosStoreProductDiscount, wrapped.priceFormatter())
        }
    public actual val introductoryDiscount: StoreProductDiscount? =
        wrapped.introductoryDiscount()?.let { StoreProductDiscount(it, wrapped.priceFormatter()) }
    public actual val purchasingData: PurchasingData = IosPurchasingData(wrapped)
    public actual val presentedOfferingContext: PresentedOfferingContext? = null
}
