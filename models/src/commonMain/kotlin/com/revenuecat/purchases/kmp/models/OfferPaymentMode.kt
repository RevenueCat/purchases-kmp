package com.revenuecat.purchases.kmp.models

/**
 * Play Store only. Payment mode for offer pricing phases
 */
public enum class OfferPaymentMode {
    /**
     * Subscribers don't pay until the specified period ends
     */
    FREE_TRIAL,

    /**
     * Subscribers pay up front for a specified period
     */
    SINGLE_PAYMENT,

    /**
     * Subscribers pay a discounted amount for a specified number of periods
     */
    DISCOUNTED_RECURRING_PAYMENT,
}
