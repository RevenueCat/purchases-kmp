package com.revenuecat.purchases.kmp

/**
 * This class contains all the offerings configured in RevenueCat dashboard.
 * For more info see https://docs.revenuecat.com/docs/entitlements
 */
public data class Offerings(
    /**
     * Dictionary of all Offerings [Offering] objects keyed by their identifier.
     */
    val all: Map<String, Offering>,

    /**
     * Current offering configured in the RevenueCat dashboard.
     */
    val current: Offering?
) {
    /**
     * Retrieves an specific offering by its identifier. It's equivalent to
     * calling [getOffering(identifier)]
     * @param identifier Offering identifier
     */
    public operator fun get(identifier: String): Offering? = all[identifier]

    /**
     * Retrieves an specific offering by its identifier.
     * @param identifier Offering identifier
     */
    public fun getOffering(identifier: String): Offering? = all[identifier]
}
