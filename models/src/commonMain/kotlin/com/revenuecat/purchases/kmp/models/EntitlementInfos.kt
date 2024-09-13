package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.models.VerificationResult

/**
 * This class contains all the entitlements associated to the user.
 */
public class EntitlementInfos(

    /**
     * Map of all EntitlementInfo [EntitlementInfo] objects (active and inactive) keyed by entitlement
     * identifier.
     */
    public val all: Map<String, EntitlementInfo>,

    /**
     * If entitlement verification was enabled, the result of that verification. If not,
     * [VerificationResult.NOT_REQUESTED].
     */
    public val verification: VerificationResult
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
