package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import kotlin.jvm.JvmInline

/**
 * Common ad mediator names.
 */
@ExperimentalRevenueCatApi
@JvmInline
public value class AdMediatorName(public val value: String) {
    public companion object {
        public val AD_MOB: AdMediatorName = AdMediatorName("AdMob")
        public val APP_LOVIN: AdMediatorName = AdMediatorName("AppLovin")
    }
}
