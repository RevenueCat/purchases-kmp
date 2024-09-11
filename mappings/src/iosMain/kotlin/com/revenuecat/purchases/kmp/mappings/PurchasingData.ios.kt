package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.ProductType
import com.revenuecat.purchases.kmp.models.PurchasingData
import cocoapods.PurchasesHybridCommon.RCStoreProduct as NativeIosStoreProduct

internal class IosPurchasingData(
    product: NativeIosStoreProduct
): PurchasingData {
    override val productId: String = product.productIdentifier()
    override val productType: ProductType = product.productType().toProductType()
}
