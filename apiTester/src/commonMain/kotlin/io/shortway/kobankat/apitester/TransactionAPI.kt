package io.shortway.kobankat.apitester

import io.shortway.kobankat.datetime.purchaseInstant
import io.shortway.kobankat.models.Transaction
import io.shortway.kobankat.models.productIdentifier
import io.shortway.kobankat.models.purchaseDateMillis
import io.shortway.kobankat.models.transactionIdentifier
import kotlinx.datetime.Instant

@Suppress("unused", "UNUSED_VARIABLE")
private class TransactionAPI {
    fun check(transaction: Transaction) {
        with(transaction) {
            val transactionIdentifier: String = transactionIdentifier
            val productIdentifier: String = productIdentifier
            val purchaseDate: Long = purchaseDateMillis
            val purchaseInstant: Instant = purchaseInstant
        }
    }
}
