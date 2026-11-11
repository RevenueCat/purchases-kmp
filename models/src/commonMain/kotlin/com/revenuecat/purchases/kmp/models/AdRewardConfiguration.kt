package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import kotlin.time.Duration

/** A configured reward. Future or unmodeled reward shapes are represented by [UnsupportedReward]. */
@ExperimentalRevenueCatApi
public sealed interface AdRewardConfiguration {
    /**
     * A configured virtual currency reward.
     * @property code The virtual currency code defined in the RevenueCat dashboard.
     * @property amount The positive configured amount.
     */
    public data class VirtualCurrency(public val code: String, public val amount: Int) : AdRewardConfiguration {
        init {
            require(code.isNotEmpty() && amount > 0) { "A currency reward requires a code and positive amount" }
        }
    }

    /**
     * A configured entitlement reward, describing a duration rather than a grant expiration.
     * @property identifier The entitlement identifier.
     * @property duration The positive, finite duration for which access will be granted.
     */
    public data class Entitlement(
        public val identifier: String,
        public val duration: Duration,
    ) : AdRewardConfiguration {
        init {
            require(identifier.isNotEmpty() && duration.isPositive() && duration.isFinite()) {
                "An entitlement reward requires an identifier and positive finite duration"
            }
        }

        /** The configured duration in seconds, also accessible from Java. */
        public val durationInSeconds: Long
            get() = duration.inWholeSeconds
    }

    /** A configured reward shape that this SDK version cannot model. */
    public object UnsupportedReward : AdRewardConfiguration
}
