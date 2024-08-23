package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.models.ProductCategory
import com.revenuecat.purchases.kmp.models.PurchasingData
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.SubscriptionOptions

@Suppress("unused", "UNUSED_VARIABLE", "LongMethod")
private class StoreProductAPI {
    fun check(product: StoreProduct) {
        with(product) {
            val storeProductId: String = id
            val type: ProductType = type
            val category: ProductCategory? = category
            val price: Price = price
            val title: String = title
            val description: String? = localizedDescription
            val period: Period? = period
            val subscriptionOptions: SubscriptionOptions? = subscriptionOptions
            val defaultOption: SubscriptionOption? = defaultOption
            val discounts: List<StoreProductDiscount> = discounts
            val introDiscount: StoreProductDiscount? = introductoryDiscount
            val purchasingData: PurchasingData = purchasingData
            val presentedOfferingContext: PresentedOfferingContext? = presentedOfferingContext
        }
    }

}
