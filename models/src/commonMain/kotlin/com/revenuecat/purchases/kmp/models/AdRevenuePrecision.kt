package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi

/**
 * Represents the precision level of ad revenue values reported by ad networks.
 *
 * Different ad networks provide revenue data with varying levels of accuracy.
 * This enum helps distinguish between exact reported values and estimates.
 */
@ExperimentalRevenueCatApi
public class AdRevenuePrecision(public val value: String) {
    public companion object {
        /**
         * The revenue value is exact and confirmed by the ad network.
         * This is the most accurate precision level, typically used when the ad network
         * provides verified, final revenue figures.
         */
        public val EXACT: AdRevenuePrecision = AdRevenuePrecision("exact")

        /**
         * The revenue value was defined by the publisher (app developer).
         * This precision level indicates that the revenue amount is set by the publisher
         * rather than being reported directly from the ad network, often used in
         * custom ad implementations or manual tracking scenarios.
         */
        public val PUBLISHER_DEFINED: AdRevenuePrecision = AdRevenuePrecision("publisher_defined")

        /**
         * The revenue value is an estimate from the ad network.
         * Ad networks may provide estimated revenue figures before final values are available,
         * or when exact revenue data is not accessible. These estimates may be adjusted later.
         */
        public val ESTIMATED: AdRevenuePrecision = AdRevenuePrecision("estimated")

        /**
         * The precision level of the revenue value is unknown or not specified.
         * This is used when the ad network does not provide information about the
         * accuracy of the revenue data, or when the precision cannot be determined.
         */
        public val UNKNOWN: AdRevenuePrecision = AdRevenuePrecision("unknown")
    }
}
