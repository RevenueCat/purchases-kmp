package com.revenuecat.purchases.kmp.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
) {
    /**
     * The purchase date.
     */
    @ExperimentalTime
    public val purchaseDate: Instant by lazy {
        Instant.fromEpochMilliseconds(purchaseDateMillis)
    }
}
