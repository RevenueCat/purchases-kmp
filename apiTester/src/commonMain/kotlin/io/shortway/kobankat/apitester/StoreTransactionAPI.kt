package io.shortway.kobankat.apitester

import io.shortway.kobankat.models.StoreTransaction
import io.shortway.kobankat.models.productIds
import io.shortway.kobankat.models.purchaseTime
import io.shortway.kobankat.models.transactionId

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
