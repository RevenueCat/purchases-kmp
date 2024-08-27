package com.revenuecat.purchases.kmp.models

/**
 * Represents an in-app billing purchase.
 */
public class StoreTransaction(
    /**
     * Unique (per store) order identifier for the purchased transaction. Always null for Amazon.
     * Only available for non-restored Google purchases for Google Play.
     */
    public val transactionId: String?,

    /**
     * Product IDs purchased.
     *
     * Size can be greater than 1 on Android. This indicates that a multi-line purchase occurred,
     * which RevenueCat does not support.
     * Only the first productId will be processed by the SDK.
     */
    public val productIds: List<String>,

    /**
     * Time the product was purchased, in milliseconds since the epoch.
     */
    public val purchaseTime: Long,
)
