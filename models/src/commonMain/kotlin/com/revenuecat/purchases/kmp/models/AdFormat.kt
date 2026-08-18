package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.InternalRevenueCatApi

/**
 * Common ad format types.
 */
@ExperimentalRevenueCatApi
public class AdFormat internal constructor(@property:InternalRevenueCatApi public val value: String) {
    @OptIn(InternalRevenueCatApi::class)
    public override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AdFormat) return false
        return value == other.value
    }

    @OptIn(InternalRevenueCatApi::class)
    public override fun hashCode(): Int {
        return value.hashCode()
    }

    public companion object {
        public val OTHER: AdFormat = AdFormat("other")
        public val BANNER: AdFormat = AdFormat("banner")
        public val INTERSTITIAL: AdFormat = AdFormat("interstitial")
        public val REWARDED: AdFormat = AdFormat("rewarded")
        public val REWARDED_INTERSTITIAL: AdFormat = AdFormat("rewarded_interstitial")
        public val NATIVE: AdFormat = AdFormat("native")
        public val APP_OPEN: AdFormat = AdFormat("app_open")

        public fun fromString(value: String): AdFormat {
            return when (value.trim()) {
                "other" -> OTHER
                "banner" -> BANNER
                "interstitial" -> INTERSTITIAL
                "rewarded" -> REWARDED
                "rewarded_interstitial" -> REWARDED_INTERSTITIAL
                "native" -> NATIVE
                "app_open" -> APP_OPEN
                else -> AdFormat(value)
            }
        }
    }
}
