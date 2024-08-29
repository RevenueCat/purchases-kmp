package com.revenuecat.purchases.kmp.models

/**
 * Enum mapping billing feature types. Currently only used to check for feature availability
 * in Android's, Play store.
 */
public enum class BillingFeature {
    SUBSCRIPTIONS,
    SUBSCRIPTIONS_UPDATE,
    PRICE_CHANGE_CONFIRMATION,
}
