package com.revenuecat.purchases.kmp.models

/**
 * Represents an in-app billing purchase.
 */
public data class StoreTransaction(
    /**
     * Unique (per store) order identifier for the purchased transaction. Always null for Amazon.
     * Only available for non-restored Google purchases for Google Play.
     */
    val orderId: String?,

    /**
     * Product IDs purchased.
     *
     * Size can be greater than 1 on Android. This indicates that a multi-line purchase occurred,
     * which RevenueCat does not support.
     * Only the first productId will be processed by the SDK.
     */
    val productIds: List<String>,

    /**
     * Time the product was purchased, in milliseconds since the epoch.
     */
    val purchaseTime: Long,
) {
    public companion object
}
