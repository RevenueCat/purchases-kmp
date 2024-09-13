package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.models.ProductCategory
import com.revenuecat.purchases.kmp.models.ProductType
import com.revenuecat.purchases.kmp.models.PurchasingData
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.SubscriptionOptions
import com.revenuecat.purchases.models.StoreProduct as NativeAndroidStoreProduct

public fun StoreProduct.toAndroidStoreProduct(): NativeAndroidStoreProduct = (this as AndroidStoreProduct).wrapped

public fun NativeAndroidStoreProduct.toStoreProduct(): StoreProduct = AndroidStoreProduct(this)

private class AndroidStoreProduct(
    val wrapped: NativeAndroidStoreProduct,
): StoreProduct {
    override val id: String = wrapped.id
    override val type: ProductType = wrapped.type.toProductType()
    override val category: ProductCategory? = type.toProductCategoryOrNull()
    override val price: Price = wrapped.price.toPrice()
    override val title: String = wrapped.title
    override val localizedDescription: String = wrapped.description
    override val period: Period? = wrapped.period?.toPeriod()
    override val subscriptionOptions: SubscriptionOptions? = wrapped.subscriptionOptions?.toSubscriptionOptions()
    override val defaultOption: SubscriptionOption? = wrapped.defaultOption?.toSubscriptionOption()
    override val discounts: List<StoreProductDiscount> = emptyList()
    override val introductoryDiscount: StoreProductDiscount? = null
    override val purchasingData: PurchasingData = AndroidPurchasingData(wrapped.purchasingData)
    override val presentedOfferingContext: PresentedOfferingContext? = wrapped.presentedOfferingContext?.toPresentedOfferingContext()
}
