package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.StoreProduct

/**
 * Contains information about the product available for the user to purchase. For more info see
 * [/docs/entitlements](https://docs.revenuecat.com/docs/entitlements).
 * @property identifier Unique identifier for this package. Can be one a predefined package type or a custom one.
 * @property packageType Package type for the product. Will be one of [PackageType].
 * @property storeProduct [StoreProduct] assigned to this package.
 * @property presentedOfferingContext [PresentedOfferingContext] from which this package was obtained.
 * @property offeringIdentifier offering this package was returned from.
 */
public expect class Package

public expect val Package.identifier: String
public expect val Package.packageType: PackageType
public expect val Package.storeProduct: StoreProduct
public expect val Package.presentedOfferingContext: PresentedOfferingContext

/**
 *  Enumeration of all possible Package types.
 */
public expect enum class PackageType {
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

public val PackageType.identifier: String?
    get() = when (this) {
        PackageType.UNKNOWN -> null
        PackageType.CUSTOM -> null
        PackageType.LIFETIME -> "\$rc_lifetime"
        PackageType.ANNUAL -> "\$rc_annual"
        PackageType.SIX_MONTH -> "\$rc_six_month"
        PackageType.THREE_MONTH -> "\$rc_three_month"
        PackageType.TWO_MONTH -> "\$rc_two_month"
        PackageType.MONTHLY -> "\$rc_monthly"
        PackageType.WEEKLY -> "\$rc_weekly"
        else -> error("Unexpected PackageType: $this")
    }
