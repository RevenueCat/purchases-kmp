package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.InternalRevenueCatApi

/**
 * Common ad mediator names.
 */
@ExperimentalRevenueCatApi
public class AdMediatorName internal constructor(@property:InternalRevenueCatApi public val value: String) {
    @OptIn(InternalRevenueCatApi::class)
    public override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AdMediatorName) return false
        return value == other.value
    }

    @OptIn(InternalRevenueCatApi::class)
    public override fun hashCode(): Int {
        return value.hashCode()
    }

    public companion object {
        public val AD_MOB: AdMediatorName = AdMediatorName("AdMob")
        public val APP_LOVIN: AdMediatorName = AdMediatorName("AppLovin")

        internal fun fromString(value: String): AdMediatorName {
            return when (value.trim()) {
                "AdMob" -> AD_MOB
                "AppLovin" -> APP_LOVIN
                else -> AdMediatorName(value)
            }
        }
    }
}
