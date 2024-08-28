package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.StoreTransaction

@Suppress("unused", "UNUSED_VARIABLE", "LongMethod")
private class StoreTransactionAPI {
    fun check(transaction: StoreTransaction) {
        with(transaction) {
            val orderId: String? = transactionId
            val productIds: List<String> = productIds
            val purchaseTime: Long = purchaseTime
        }
    }
}
