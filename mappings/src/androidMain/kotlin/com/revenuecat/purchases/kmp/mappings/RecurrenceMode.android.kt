package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.RecurrenceMode
import com.revenuecat.purchases.models.RecurrenceMode as AndroidRecurrenceMode

internal fun AndroidRecurrenceMode.toRecurrenceMode(): RecurrenceMode =
    when (this) {
        AndroidRecurrenceMode.INFINITE_RECURRING -> RecurrenceMode.INFINITE_RECURRING
        AndroidRecurrenceMode.FINITE_RECURRING -> RecurrenceMode.FINITE_RECURRING
        AndroidRecurrenceMode.NON_RECURRING -> RecurrenceMode.NON_RECURRING
        AndroidRecurrenceMode.UNKNOWN -> RecurrenceMode.UNKNOWN
    }
