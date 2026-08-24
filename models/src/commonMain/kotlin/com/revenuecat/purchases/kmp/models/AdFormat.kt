package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.InternalRevenueCatApi
import dev.drewhamilton.poko.Poko

/**
 * Common ad format types.
 */
@Poko
public class AdFormat internal constructor(@property:InternalRevenueCatApi public val value: String) {
    public companion object {
        public val OTHER: AdFormat = AdFormat("other")
        public val BANNER: AdFormat = AdFormat("banner")
        public val INTERSTITIAL: AdFormat = AdFormat("interstitial")
        public val REWARDED: AdFormat = AdFormat("rewarded")
        public val REWARDED_INTERSTITIAL: AdFormat = AdFormat("rewarded_interstitial")
        public val NATIVE: AdFormat = AdFormat("native")
        public val APP_OPEN: AdFormat = AdFormat("app_open")

        internal fun fromString(value: String): AdFormat {
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
