package com.revenuecat.purchases.kmp.models

/**
 * This class contains all the offerings configured in RevenueCat dashboard.
 * For more info see https://docs.revenuecat.com/docs/entitlements
 */
public class Offerings(
    /**
     * Dictionary of all Offerings [Offering] objects keyed by their identifier.
     */
    public val all: Map<String, Offering>,

    /**
     * Current offering configured in the RevenueCat dashboard.
     */
    public val current: Offering?,

    /**
     * An implementation to retrieves a specific offering by placement identifier.
     */
    private val getCurrentOfferingForPlacement: (placementId: String) -> Offering?
) {

    // @JvmOverloads doesn't work for ObjC.
    public constructor(
        /**
         * Dictionary of all Offerings [Offering] objects keyed by their identifier.
         */
        all: Map<String, Offering>,

        /**
         * Current offering configured in the RevenueCat dashboard.
         */
        current: Offering?,
    ) : this(
        all = all,
        current = current,
        getCurrentOfferingForPlacement = { null }
    )

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

    /**
     * Retrieves a specific offering by placement identifier. For more info see the
     * [RevenueCat docs](https://www.revenuecat.com/docs/tools/targeting).
     */
    public fun getCurrentOfferingForPlacement(placementId: String): Offering? =
        getCurrentOfferingForPlacement.invoke(placementId)
}
