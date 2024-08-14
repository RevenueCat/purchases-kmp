package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreTransaction
import com.revenuecat.purchases.kmp.ktx.toEpochMilliseconds
import platform.Foundation.NSDate

public actual class StoreTransaction private constructor(
    internal val transactionId: String?,
    internal val productIds: List<String>,
    internal val purchaseTime: Long
) {

    internal constructor(
        rcTransaction: RCStoreTransaction
    ) : this(
        transactionId = rcTransaction.transactionIdentifier(),
        productIds = listOf(rcTransaction.productIdentifier()),
        purchaseTime = rcTransaction.purchaseDate().toEpochMilliseconds()
    )

    internal constructor(
        transactionId: String,
        productId: String,
        purchaseTime: Long,
    ) : this(
        transactionId = transactionId,
        productIds = listOf(productId),
        purchaseTime = purchaseTime
    )

    internal companion object {
        fun fromMap(storeTransactionMap: Map<Any?, *>): Result<StoreTransaction> {
            val transactionId = storeTransactionMap["transactionIdentifier"] as? String
            val productId = storeTransactionMap["productIdentifier"] as? String
            val purchaseTime = (storeTransactionMap["purchaseDateMillis"] as? Double)?.toLong()

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
                    productId = productId,
                    purchaseTime = purchaseTime
                )
            )
        }
    }

}

public actual val StoreTransaction.transactionId: String?
    get() = transactionId

public actual val StoreTransaction.productIds: List<String>
    get() = productIds

public actual val StoreTransaction.purchaseTime: Long
    get() = purchaseTime
