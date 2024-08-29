package com.revenuecat.purchases.kmp

/**
 * Possible product types.
 */
public enum class ProductType {
    /**
     * A subscription product.
     */
    SUBS,

    /**
     * An in-app purchase product.
     */
    INAPP,

    /**
     * The product type could not be determined.
     */
    UNKNOWN,
}
