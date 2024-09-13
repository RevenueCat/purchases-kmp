package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.models.ProductCategory
import com.revenuecat.purchases.kmp.models.ProductType
import com.revenuecat.purchases.kmp.models.PurchasingData
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.SubscriptionOptions
import cocoapods.PurchasesHybridCommon.RCStoreProduct as NativeIosStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreProductDiscount as IosStoreProductDiscount

public fun NativeIosStoreProduct.toStoreProduct(): StoreProduct =
    IosStoreProduct(this)

public fun StoreProduct.toIosStoreProduct(): NativeIosStoreProduct =
    (this as IosStoreProduct).wrapped

private class IosStoreProduct(val wrapped: NativeIosStoreProduct): StoreProduct {
    override val id: String = wrapped.productIdentifier()
    override val type: ProductType = wrapped.productType().toProductType()
    override val category: ProductCategory = wrapped.productCategory().toProductCategory()
    override val price: Price = wrapped.toPrice()
    override val title: String = wrapped.localizedTitle()
    override val localizedDescription: String = wrapped.localizedDescription()
    override val period: Period? = wrapped.subscriptionPeriod()?.toPeriod()
    override val subscriptionOptions: SubscriptionOptions? = null
    override val defaultOption: SubscriptionOption? = null
    override val discounts: List<StoreProductDiscount> = wrapped.discounts()
        .map { it as IosStoreProductDiscount }
        .map { it.toStoreProductDiscount() }
    override val introductoryDiscount: StoreProductDiscount? =
        wrapped.introductoryDiscount()?.toStoreProductDiscount()
    override val purchasingData: PurchasingData = IosPurchasingData(wrapped)
    override val presentedOfferingContext: PresentedOfferingContext? = null
}
