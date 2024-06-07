package io.shortway.kobankat.apitester

import io.shortway.kobankat.PresentedOfferingContext
import io.shortway.kobankat.ProductType
import io.shortway.kobankat.i18n.Locale
import io.shortway.kobankat.models.Period
import io.shortway.kobankat.models.Price
import io.shortway.kobankat.models.PurchasingData
import io.shortway.kobankat.models.StoreProduct
import io.shortway.kobankat.models.StoreProductDiscount
import io.shortway.kobankat.models.SubscriptionOption
import io.shortway.kobankat.models.SubscriptionOptions
import io.shortway.kobankat.models.defaultOption
import io.shortway.kobankat.models.description
import io.shortway.kobankat.models.discounts
import io.shortway.kobankat.models.id
import io.shortway.kobankat.models.introductoryDiscount
import io.shortway.kobankat.models.period
import io.shortway.kobankat.models.presentedOfferingContext
import io.shortway.kobankat.models.price
import io.shortway.kobankat.models.purchasingData
import io.shortway.kobankat.models.subscriptionOptions
import io.shortway.kobankat.models.title
import io.shortway.kobankat.models.type

@Suppress("unused", "UNUSED_VARIABLE", "LongMethod")
private class StoreProductAPI {
    fun check(product: StoreProduct) {
        val locale = Locale.Default
        with(product) {
            val storeProductId: String = id
            val type: ProductType = type
            val price: Price = price
            val title: String = title
            val description: String? = description
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
