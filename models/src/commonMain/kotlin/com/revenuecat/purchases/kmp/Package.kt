package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.StoreProduct

/**
 * Contains information about the product available for the user to purchase. For more info see
 * [/docs/entitlements](https://docs.revenuecat.com/docs/entitlements).
 * @property identifier Unique identifier for this package. Can be one a predefined package type or a custom one.
 * @property packageType Package type for the product. Will be one of [PackageType].
 * @property storeProduct [StoreProduct] assigned to this package.
 * @property presentedOfferingContext [PresentedOfferingContext] from which this package was obtained.
 */
public interface Package {
    public val identifier: String
    public val packageType: PackageType
    public val storeProduct: StoreProduct
    public val presentedOfferingContext: PresentedOfferingContext
}

/**
 *  Enumeration of all possible Package types.
 */
public enum class PackageType {
    /**
     * A package that was defined with a custom identifier.
     */
    UNKNOWN,

    /**
     * A package that was defined with a custom identifier.
     */
    CUSTOM,

    /**
     * A package configured with the predefined lifetime identifier.
     */
    LIFETIME,

    /**
     * A package configured with the predefined annual identifier.
     */
    ANNUAL,

    /**
     * A package configured with the predefined six month identifier.
     */
    SIX_MONTH,

    /**
     * A package configured with the predefined three month identifier.
     */
    THREE_MONTH,

    /**
     * A package configured with the predefined two month identifier.
     */
    TWO_MONTH,

    /**
     * A package configured with the predefined monthly identifier.
     */
    MONTHLY,

    /**
     * A package configured with the predefined weekly identifier.
     */
    WEEKLY,
}
