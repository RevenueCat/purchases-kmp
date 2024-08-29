package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.StoreTransaction
import cocoapods.PurchasesHybridCommon.RCStoreTransaction as IosStoreTransaction

public fun IosStoreTransaction.toStoreTransaction(): StoreTransaction =
    StoreTransaction(
        transactionId = transactionIdentifier(),
        productIds = listOf(productIdentifier()),
        purchaseTime = purchaseDate().toEpochMilliseconds(),
    )

public fun buildStoreTransaction(storeTransactionMap: Map<Any?, *>): Result<StoreTransaction> {
    val transactionId = storeTransactionMap["transactionIdentifier"] as? String
    val productId = storeTransactionMap["productIdentifier"] as? String
    val purchaseTime = (storeTransactionMap["purchaseDateMillis"] as? Number)?.toLong()

    if(transactionId == null) {
        return Result.failure(IllegalArgumentException("Expected a non-null transactionIdentifier"))
    }

    if(productId == null) {
        return Result.failure(IllegalArgumentException("Expected a non-null productIdentifier"))
    }

    if(purchaseTime == null) {
        return Result.failure(IllegalArgumentException("Expected a non-null purchaseDateMillis"))
    }

    return Result.success(
        StoreTransaction(
            transactionId = transactionId,
            productIds = listOf(productId),
            purchaseTime = purchaseTime
        )
    )
}
