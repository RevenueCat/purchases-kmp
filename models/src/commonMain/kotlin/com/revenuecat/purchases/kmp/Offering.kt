package com.revenuecat.purchases.kmp

/**
 * An offering is a collection of [Package]s available for the user to purchase.
 * For more info see https://docs.revenuecat.com/docs/entitlements
 */
public interface Offering {
    /**
     * Unique identifier defined in RevenueCat dashboard.
     */
    public val identifier: String

    /**
     * Offering description defined in RevenueCat dashboard.
     */
    public val serverDescription: String

    /**
     * Offering metadata defined in RevenueCat dashboard.
     */
    public val metadata: Map<String, Any>

    /**
     * Array of [Package] objects available for purchase.
     */
    public val availablePackages: List<Package>

    /**
     * Lifetime package type configured in the RevenueCat dashboard, if available.
     */
    public val lifetime: Package?

    /**
     * Annual package type configured in the RevenueCat dashboard, if available.
     */
    public val annual: Package?

    /**
     * Six month package type configured in the RevenueCat dashboard, if available.
     */
    public val sixMonth: Package?

    /**
     * Three month package type configured in the RevenueCat dashboard, if available.
     */
    public val threeMonth: Package?

    /**
     * Two month package type configured in the RevenueCat dashboard, if available.
     */
    public val twoMonth: Package?

    /**
     * Monthly package type configured in the RevenueCat dashboard, if available.
     */
    public val monthly: Package?

    /**
     * Weekly package type configured in the RevenueCat dashboard, if available.
     */
    public val weekly: Package?

    /**
     * Retrieves a specific package by identifier, use this to access custom package types configured
     * in the RevenueCat dashboard
     */
    public fun getPackage(identifier: String): Package? =
        availablePackages.firstOrNull { it.identifier == identifier }

    /**
     * Returns the `metadata` value associated to `key` for the expected `String` type
     * or `default` if not found, or it's not the expected `String` type.
     */
    public fun getMetadataString(key: String, default: String): String =
        metadata[key] as? String ?: default

    /**
     * Retrieves a specific package by identifier, use this to access custom package types configured
     * in the RevenueCat dashboard. Equivalent to calling [getPackage][Offering.getPackage].
     */
    public operator fun get(s: String): Package? = getPackage(s)
}
