package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.InternalRevenueCatApi
import dev.drewhamilton.poko.Poko

/**
 * Common ad mediator names.
 */
@ExperimentalRevenueCatApi
@Poko
public class AdMediatorName internal constructor(@property:InternalRevenueCatApi public val value: String) {
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
