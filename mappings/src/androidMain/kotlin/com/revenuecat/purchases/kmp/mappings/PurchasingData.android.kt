package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.models.PurchasingData
import com.revenuecat.purchases.models.PurchasingData as NativeAndroidPurchasingData

internal class AndroidPurchasingData internal constructor(
    wrapped: NativeAndroidPurchasingData
): PurchasingData {
    override val productId: String = wrapped.productId
    override val productType: ProductType = wrapped.productType.toProductType()
}
