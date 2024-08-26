package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct as NativeIosStoreProduct
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.toProductType

internal class IosPurchasingData(
    product: NativeIosStoreProduct
): PurchasingData {
    override val productId: String = product.productIdentifier()
    override val productType: ProductType = product.productType().toProductType()
}
