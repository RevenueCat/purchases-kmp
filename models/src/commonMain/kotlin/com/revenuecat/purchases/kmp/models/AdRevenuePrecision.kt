package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import kotlin.jvm.JvmInline

/**
 * Common ad revenue precision values.
 */
@ExperimentalRevenueCatApi
@JvmInline
public value class AdRevenuePrecision(public val value: String) {
    public companion object {
        public val EXACT: AdRevenuePrecision = AdRevenuePrecision("exact")
        public val PUBLISHER_DEFINED: AdRevenuePrecision = AdRevenuePrecision("publisher_defined")
        public val ESTIMATED: AdRevenuePrecision = AdRevenuePrecision("estimated")
        public val UNKNOWN: AdRevenuePrecision = AdRevenuePrecision("unknown")
    }
}
