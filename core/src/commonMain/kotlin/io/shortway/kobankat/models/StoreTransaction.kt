package io.shortway.kobankat.models

public expect class StoreTransaction

/**
 * Unique (per store) order identifier for the purchased transaction. Always null for Amazon.
 * Only available for non-restored Google purchases for Google Play.
 */
public expect val StoreTransaction.transactionId: String?

/**
 * Product IDs purchased.
 *
 * Size can be greater than 1 on Android. This indicates that a multi-line purchase occurred,
 * which RevenueCat does not support.
 * Only the first productId will be processed by the SDK.
 */
public expect val StoreTransaction.productIds: List<String>

/**
 * Time the product was purchased, in milliseconds since the epoch.
 */
public expect val StoreTransaction.purchaseTime: Long

public enum class PurchaseType {
    GOOGLE_PURCHASE,
    GOOGLE_RESTORED_PURCHASE,
    AMAZON_PURCHASE,
    APP_STORE_PURCHASE,
}