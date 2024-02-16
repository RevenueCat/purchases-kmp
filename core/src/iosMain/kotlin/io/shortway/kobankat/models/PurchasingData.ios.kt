package io.shortway.kobankat.models

import cocoapods.RevenueCat.RCStoreProduct
import io.shortway.kobankat.ProductType
import io.shortway.kobankat.toProductType

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