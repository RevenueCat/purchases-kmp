package com.revenuecat.purchases.kmp.models

/**
 * Enum mapping in-app message types.
 */
public enum class StoreMessageType {
    /**
     * In-app messages for billing issues.
     * If the user has had a payment declined, this will show a toast notification notifying them
     * and providing instructions for recovery of the subscription.
     */
    BILLING_ISSUES,

    /**
     * App Store only. Message shown when there is a price increase in a subscription that requires
     * consent.
     */
    PRICE_INCREASE_CONSENT,

    /**
     * App Store only. Generic store messages.
     */
    GENERIC,

    /**
     * App Store only. This message will show if the subscriber is eligible for an iOS win-back
     * offer and will allow the subscriber to redeem the offer.
     */
    WIN_BACK_OFFER,
}
