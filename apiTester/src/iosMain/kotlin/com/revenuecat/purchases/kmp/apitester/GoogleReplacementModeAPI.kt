package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.GoogleReplacementMode

@Suppress("unused")
private class GoogleReplacementModeAPI {
    fun check(mode: GoogleReplacementMode) {
        when (mode) {
            GoogleReplacementMode.WITHOUT_PRORATION,
            GoogleReplacementMode.WITH_TIME_PRORATION,
            GoogleReplacementMode.CHARGE_FULL_PRICE,
            GoogleReplacementMode.CHARGE_PRORATED_PRICE,
            -> {
            }
        }.exhaustive
    }
}
