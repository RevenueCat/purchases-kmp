package com.revenuecat.purchases.kmp.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Reward payload delivered when server-side reward verification succeeds.
 */
public sealed class VerifiedReward {

    /**
     * A virtual-currency reward with a code and amount.
     */
    public class VirtualCurrency(
        public val code: String,
        public val amount: Int,
    ) : VerifiedReward()

    /**
     * A temporary entitlement grant.
     *
     * @property identifier The entitlement identifier.
     * @property expiresAtMillis The grant expiration, in milliseconds since epoch.
     */
    public class Entitlement(
        public val identifier: String,
        public val expiresAtMillis: Long,
    ) : VerifiedReward() {
        @OptIn(ExperimentalTime::class)
        public val expiresAt: Instant
            get() = Instant.fromEpochMilliseconds(expiresAtMillis)
    }

    /**
     * Verification succeeded but no reward was granted.
     */
    public object NoReward : VerifiedReward()

    /**
     * Verification succeeded but the reward type is not modeled by this SDK version.
     */
    public object UnsupportedReward : VerifiedReward()
}
