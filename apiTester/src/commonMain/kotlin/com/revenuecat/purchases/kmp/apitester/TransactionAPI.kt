package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.datetime.purchaseInstant
import com.revenuecat.purchases.kmp.models.Transaction
import kotlin.time.ExperimentalTime

@Suppress("unused", "UNUSED_VARIABLE")
private class TransactionAPI {
    @OptIn(ExperimentalTime::class)
    fun check(transaction: Transaction) {
        with(transaction) {
            val transactionIdentifier: String = transactionIdentifier
            val productIdentifier: String = productIdentifier
            val purchaseDateMillis: Long = purchaseDateMillis
            val purchaseInstant: kotlinx.datetime.Instant = purchaseInstant
            val purchaseDate: kotlin.time.Instant = purchaseDate
        }
    }
}
