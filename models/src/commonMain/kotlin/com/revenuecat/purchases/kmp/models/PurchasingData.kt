package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ProductType

/**
 * Data connected to a purchase.
 */
public interface PurchasingData {
    public val productId: String
    public val productType: ProductType
}
