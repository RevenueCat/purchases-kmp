package com.revenuecat.purchases.kmp.models

/**
 * Represents an in-app billing purchase.
 */
public class StoreTransaction(

    /**
     * Unique (per store) order identifier for the purchased transaction. Always null for Amazon.
     * Only available for non-restored Google purchases for Google Play.
     */
    public val transactionId: String,

    /**
     * Product IDs purchased.
     *
     * Size can be greater than 1 on Android. This indicates that a multi-line purchase occurred,
     * which RevenueCat does not support.
     * Only the first productId will be processed by the SDK.
     */
    public val productIDs: List<String>,

    /**
     * Time the product was purchased, in milliseconds since the epoch.
     */
    public val purchaseTime: Long
) {
    public constructor(map: Map<String, Any>) : this(
        transactionId = map["transactionIdentifier"] as? String
            ?: throw IllegalArgumentException("transactionIdentifier is missing or not a String"),
        productIDs = map["productIdentifier"]?.let { listOf(it as String) }
            ?: throw IllegalArgumentException("productIdentifier is missing or not a String"),
        purchaseTime = map["purchaseDateMillis"] as? Long
            ?: throw IllegalArgumentException("purchaseDateMillis is missing or not a Long")
    )
}

///**
// * Represents an in-app billing purchase.
// */
//public expect class StoreTransaction {
//
//    /**
//     * Unique (per store) order identifier for the purchased transaction. Always null for Amazon.
//     * Only available for non-restored Google purchases for Google Play.
//     */
//    public val transactionId: String
//
//    /**
//     * Product IDs purchased.
//     *
//     * Size can be greater than 1 on Android. This indicates that a multi-line purchase occurred,
//     * which RevenueCat does not support.
//     * Only the first productId will be processed by the SDK.
//     */
//    public val productIDs: List<String>
//
//    /**
//     * Time the product was purchased, in milliseconds since the epoch.
//     */
//    public val purchaseTime: Long
//}

///**
// * Unique (per store) order identifier for the purchased transaction. Always null for Amazon.
// * Only available for non-restored Google purchases for Google Play.
// */
//public expect val StoreTransaction.transactionId: String?
//
///**
// * Product IDs purchased.
// *
// * Size can be greater than 1 on Android. This indicates that a multi-line purchase occurred,
// * which RevenueCat does not support.
// * Only the first productId will be processed by the SDK.
// */
//public expect val StoreTransaction.productIds: List<String>
//
///**
// * Time the product was purchased, in milliseconds since the epoch.
// */
//public expect val StoreTransaction.purchaseTime: Long
