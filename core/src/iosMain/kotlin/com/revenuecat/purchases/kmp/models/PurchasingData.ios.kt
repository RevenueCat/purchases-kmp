package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import com.revenuecat.purchases.kmp.ProductType
import com.revenuecat.purchases.kmp.toProductType

public actual interface PurchasingData {
    public actual val productId: String
    public actual val productType: ProductType
}

internal fun RCStoreProduct.toPurchasingData(): PurchasingData =
    IosPurchasingData(
        productId = productIdentifier(),
        productType = productType().toProductType()
    )

private data class IosPurchasingData(
    override val productId: String,
    override val productType: ProductType
): PurchasingData
