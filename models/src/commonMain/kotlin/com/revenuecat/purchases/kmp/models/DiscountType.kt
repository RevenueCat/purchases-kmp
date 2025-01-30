package com.revenuecat.purchases.kmp.models

/**
 * App Store only. Denotes the type of a [StoreProductDiscount].
 */
public enum class DiscountType {
    /**
     * Introductory offer.
     */
    INTRODUCTORY,

    /**
     * Promotional offer for subscriptions.
     */
    PROMOTIONAL,

    /**
     * Win-back offers for subscriptions. App Store, iOS 18.0+ only.
     */
    WINBACK
}
