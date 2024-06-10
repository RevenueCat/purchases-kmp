package com.revenuecat.purchases.kmp

/**
 * An offering is a collection of [Package]s available for the user to purchase.
 * For more info see https://docs.revenuecat.com/docs/entitlements
 */
public expect class Offering

/**
 * Unique identifier defined in RevenueCat dashboard.
 */
public expect val Offering.identifier: String

/**
 * Offering description defined in RevenueCat dashboard.
 */
public expect val Offering.serverDescription: String

/**
 * Offering metadata defined in RevenueCat dashboard.
 */
public expect val Offering.metadata: Map<String, Any>

/**
 * Array of [Package] objects available for purchase.
 */
public expect val Offering.availablePackages: List<Package>

/**
 * Lifetime package type configured in the RevenueCat dashboard, if available.
 */
public expect val Offering.lifetime: Package?

/**
 * Annual package type configured in the RevenueCat dashboard, if available.
 */
public expect val Offering.annual: Package?

/**
 * Six month package type configured in the RevenueCat dashboard, if available.
 */
public expect val Offering.sixMonth: Package?

/**
 * Three month package type configured in the RevenueCat dashboard, if available.
 */
public expect val Offering.threeMonth: Package?

/**
 * Two month package type configured in the RevenueCat dashboard, if available.
 */
public expect val Offering.twoMonth: Package?

/**
 * Monthly package type configured in the RevenueCat dashboard, if available.
 */
public expect val Offering.monthly: Package?

/**
 * Weekly package type configured in the RevenueCat dashboard, if available.
 */
public expect val Offering.weekly: Package?

/**
 * Retrieves a specific package by identifier, use this to access custom package types configured
 * in the RevenueCat dashboard
 */
@Suppress("MemberVisibilityCanBePrivate")
public fun Offering.getPackage(identifier: String): Package? =
    availablePackages.firstOrNull { it.identifier == identifier }

/**
 * Returns the `metadata` value associated to `key` for the expected `String` type
 * or `default` if not found, or it's not the expected `String` type.
 */
public fun Offering.getMetadataString(key: String, default: String): String =
    metadata[key] as? String ?: default

/**
 * Retrieves a specific package by identifier, use this to access custom package types configured
 * in the RevenueCat dashboard. Equivalent to calling [getPackage][Offering.getPackage].
 */
public operator fun Offering.get(s: String): Package? = getPackage(s)
