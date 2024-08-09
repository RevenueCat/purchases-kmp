package com.revenuecat.purchases.kmp.models

/**
 * Recurrence mode for a pricing phase. Google Play Store only.
 */
public enum class RecurrenceMode {

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
