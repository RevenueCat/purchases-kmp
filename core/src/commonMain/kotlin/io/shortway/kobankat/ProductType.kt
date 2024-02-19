package io.shortway.kobankat

/**
 * Possible product types.
 */
public expect enum class ProductType {
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