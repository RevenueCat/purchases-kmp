package io.shortway.kobankat

/**
 * This class contains all the entitlements associated to the user.
 */
public expect class EntitlementInfos

/**
 * Map of all EntitlementInfo [EntitlementInfo] objects (active and inactive) keyed by entitlement
 * identifier.
 */
public expect val EntitlementInfos.all: Map<String, EntitlementInfo>

/**
 * If entitlement verification was enabled, the result of that verification. If not,
 * [VerificationResult.NOT_REQUESTED].
 */
public expect val EntitlementInfos.verification: VerificationResult

/**
 * Dictionary of active [EntitlementInfo] objects keyed by entitlement identifier.
 */
public val EntitlementInfos.active: Map<String, EntitlementInfo>
    get() = all.filter { it.value.isActive }

/**
 * Retrieves an specific entitlementInfo by its entitlement identifier. It's equivalent to
 * accessing the `all` map by entitlement identifier.
 */
public operator fun EntitlementInfos.get(s: String): EntitlementInfo? = all[s]
