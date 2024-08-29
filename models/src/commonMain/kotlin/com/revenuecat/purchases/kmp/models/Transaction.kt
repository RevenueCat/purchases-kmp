package com.revenuecat.purchases.kmp.models

/**
 * Representation of a transaction.
 */
public class Transaction(
    /**
     * Identifier of this transaction.
     */
    public val transactionIdentifier: String,
    /**
     * The identifier of the purchased product.
     */
    public val productIdentifier: String,
    /**
     * The purchase date in millis since the Unix epoch.
     */
    public val purchaseDateMillis: Long
)
