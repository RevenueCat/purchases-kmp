package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.productIds
import com.revenuecat.purchases.kmp.models.purchaseTime
import com.revenuecat.purchases.kmp.models.transactionId

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
