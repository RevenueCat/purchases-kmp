package com.revenuecat.purchases.kmp

/**
 * This class contains all the entitlements associated to the user.
 */
public data class EntitlementInfos(

    /**
     * Map of all EntitlementInfo [EntitlementInfo] objects (active and inactive) keyed by entitlement
     * identifier.
     */
    val all: Map<String, EntitlementInfo>,

    /**
     * If entitlement verification was enabled, the result of that verification. If not,
     * [VerificationResult.NOT_REQUESTED].
     */
    val verification: VerificationResult
) {
    /**
     * Dictionary of active [EntitlementInfo] objects keyed by entitlement identifier.
     */
    public val active: Map<String, EntitlementInfo>
        get() = all.filter { it.value.isActive }

    /**
     * Dictionary of active [EntitlementInfo] objects keyed by entitlement identifier.
     */
    public operator fun get(s: String): EntitlementInfo? = all[s]
}
