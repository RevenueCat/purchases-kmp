package com.revenuecat.purchases.kmp.models

/**
 * Enum mapping billing feature types
 * Allows for a common interface when calling feature eligibility methods from hybrid SDKs
 */
public expect enum class BillingFeature {
    SUBSCRIPTIONS,
    SUBSCRIPTIONS_UPDATE,
    PRICE_CHANGE_CONFIRMATION,
}

/**
 * Name according to Google Play's `@BillingClient.FeatureType`.
 */
public val BillingFeature.playBillingClientName: String
    get() = when (this) {
        BillingFeature.SUBSCRIPTIONS -> "subscriptions"
        BillingFeature.SUBSCRIPTIONS_UPDATE -> "subscriptionsUpdate"
        BillingFeature.PRICE_CHANGE_CONFIRMATION -> "priceChangeConfirmation"
        else -> error("Unexpected BillingFeature: $this")
    }
