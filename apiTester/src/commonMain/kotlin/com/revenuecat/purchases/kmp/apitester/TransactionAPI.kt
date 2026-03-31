package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.Transaction
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Suppress("unused", "UNUSED_VARIABLE")
private class TransactionAPI {
    @OptIn(ExperimentalTime::class)
    fun check(transaction: Transaction) {
        with(transaction) {
            val transactionIdentifier: String = transactionIdentifier
            val productIdentifier: String = productIdentifier
            val purchaseDateMillis: Long = purchaseDateMillis
            val purchaseDate: Instant = purchaseDate
        }
    }
}
