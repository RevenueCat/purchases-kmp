package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.InternalRevenueCatApi

/**
 * Common ad mediator names.
 */
@ExperimentalRevenueCatApi
public class AdMediatorName internal constructor(@property:InternalRevenueCatApi public val value: String) {
    public companion object {
        public val AD_MOB: AdMediatorName = AdMediatorName("AdMob")
        public val APP_LOVIN: AdMediatorName = AdMediatorName("AppLovin")

        public fun fromString(value: String): AdMediatorName {
            return when (value.trim()) {
                "AdMob" -> AD_MOB
                "AppLovin" -> APP_LOVIN
                else -> AdMediatorName(value)
            }
        }
    }
}
