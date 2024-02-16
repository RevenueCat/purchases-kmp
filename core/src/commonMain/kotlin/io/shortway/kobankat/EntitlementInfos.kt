package io.shortway.kobankat

public expect class EntitlementInfos

public expect val EntitlementInfos.all: Map<String, EntitlementInfo>
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
