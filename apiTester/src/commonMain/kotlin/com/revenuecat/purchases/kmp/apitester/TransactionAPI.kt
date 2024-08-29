package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.datetime.purchaseInstant
import com.revenuecat.purchases.kmp.models.Transaction
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
