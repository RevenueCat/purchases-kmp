package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import kotlin.jvm.JvmInline

/**
 * Common ad format types.
 */
@ExperimentalRevenueCatApi
@JvmInline
public value class AdFormat(public val value: String) {
    public companion object {
        public val OTHER: AdFormat = AdFormat("other")
        public val BANNER: AdFormat = AdFormat("banner")
        public val INTERSTITIAL: AdFormat = AdFormat("interstitial")
        public val REWARDED: AdFormat = AdFormat("rewarded")
        public val REWARDED_INTERSTITIAL: AdFormat = AdFormat("rewarded_interstitial")
        public val NATIVE: AdFormat = AdFormat("native")
        public val APP_OPEN: AdFormat = AdFormat("app_open")
        public val MREC: AdFormat = AdFormat("mrec")
    }
}
