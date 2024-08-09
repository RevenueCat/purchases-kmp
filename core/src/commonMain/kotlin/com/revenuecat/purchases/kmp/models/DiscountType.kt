package com.revenuecat.purchases.kmp.models

/**
 * App Store only. Denotes the type of a [StoreProductDiscount].
 */
public expect enum class DiscountType {
    /**
     * Introductory offer.
     */
    INTRODUCTORY,

    /**
     * Promotional offer for subscriptions.
     */
    PROMOTIONAL,
}
