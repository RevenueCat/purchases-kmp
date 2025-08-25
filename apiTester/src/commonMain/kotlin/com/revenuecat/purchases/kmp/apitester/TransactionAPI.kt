@file:OptIn(ExperimentalTime::class)

package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.datetime.purchaseInstant
import com.revenuecat.purchases.kmp.models.Transaction
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
