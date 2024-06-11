package com.revenuecat.purchases.kmp

/**
 * This class contains all the offerings configured in RevenueCat dashboard.
 * For more info see https://docs.revenuecat.com/docs/entitlements
 */
public expect class Offerings

/**
 * Current offering configured in the RevenueCat dashboard.
 */
public expect val Offerings.current: Offering?

/**
 * Dictionary of all Offerings [Offering] objects keyed by their identifier.
 */
public expect val Offerings.all: Map<String, Offering>

/**
 * Retrieves an specific offering by a placement identifier.
 * For more info see https://www.revenuecat.com/docs/tools/targeting
 * @param placementId Placement identifier
 */
public expect fun Offerings.getCurrentOfferingForPlacement(placementId: String): Offering?

/**
 * Retrieves an specific offering by its identifier.
 * @param identifier Offering identifier
 */
public fun Offerings.getOffering(identifier: String): Offering? = all[identifier]

/**
 * Retrieves an specific offering by its identifier. It's equivalent to
 * calling [getOffering(identifier)]
 * @param identifier Offering identifier
 */
public operator fun Offerings.get(identifier: String): Offering? = getOffering(identifier)
