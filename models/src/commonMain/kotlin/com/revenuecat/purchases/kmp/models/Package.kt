package com.revenuecat.purchases.kmp.models

/**
 * Contains information about the product available for the user to purchase. For more info see
 * [/docs/entitlements](https://docs.revenuecat.com/docs/entitlements).
 */
public interface Package {
    /**
     * Unique identifier for this package. Can be one a predefined package type or a custom one.
     */
    public val identifier: String

    /**
     * Package type for the product. Will be one of [PackageType].
     */
    public val packageType: PackageType

    /**
     * [StoreProduct] assigned to this package.
     */
    public val storeProduct: StoreProduct

    /**
     * [PresentedOfferingContext] from which this package was obtained.
     */
    public val presentedOfferingContext: PresentedOfferingContext

    /**
     * URL to use for web checkout for this package. Null if not available.
     */
    public val webCheckoutUrl: String?
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
