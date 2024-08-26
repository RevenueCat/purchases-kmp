package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.GoogleReplacementMode as AndroidGoogleReplacementMode

internal fun GoogleReplacementMode.toAndroidGoogleReplacementMode(): AndroidGoogleReplacementMode =
    when (this) {
        GoogleReplacementMode.WITHOUT_PRORATION -> AndroidGoogleReplacementMode.WITHOUT_PRORATION
        GoogleReplacementMode.WITH_TIME_PRORATION -> AndroidGoogleReplacementMode.WITH_TIME_PRORATION
        GoogleReplacementMode.CHARGE_PRORATED_PRICE -> AndroidGoogleReplacementMode.CHARGE_PRORATED_PRICE
        GoogleReplacementMode.CHARGE_FULL_PRICE -> AndroidGoogleReplacementMode.CHARGE_FULL_PRICE
        GoogleReplacementMode.DEFERRED -> AndroidGoogleReplacementMode.DEFERRED
    }
