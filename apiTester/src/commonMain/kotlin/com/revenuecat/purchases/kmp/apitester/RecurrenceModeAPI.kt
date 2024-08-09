package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.RecurrenceMode

@Suppress("unused")
private class RecurrenceModeAPI {
    fun check(recurrenceMode: RecurrenceMode) {
        when (recurrenceMode) {
            RecurrenceMode.INFINITE_RECURRING,
            RecurrenceMode.FINITE_RECURRING,
            RecurrenceMode.NON_RECURRING,
            RecurrenceMode.UNKNOWN,
            -> {
            }
        }.exhaustive
    }
}
