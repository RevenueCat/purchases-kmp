package io.shortway.kobankat.models

import io.shortway.kobankat.ProductType

public expect interface PurchasingData {
    public val productId: String
    public val productType: ProductType
}
