package com.revenuecat.purchases.kmp.models

/**
 * Recurrence mode for a pricing phase.
 */
public expect enum class RecurrenceMode {

    /**
     * Pricing phase repeats infinitely until cancellation.
     */
    INFINITE_RECURRING,

    /**
     * Pricing phase repeats for a fixed number of billing periods.
     */
    FINITE_RECURRING,

    /**
     * Pricing phase does not repeat.
     */
    NON_RECURRING,
    UNKNOWN,
}

public val RecurrenceMode.identifier: Int?
    get() = when(this){
        RecurrenceMode.INFINITE_RECURRING -> 1
        RecurrenceMode.FINITE_RECURRING -> 2
        RecurrenceMode.NON_RECURRING -> 3
        RecurrenceMode.UNKNOWN -> null
        else -> error("Unknown RecurrenceMode: $this")
    }
