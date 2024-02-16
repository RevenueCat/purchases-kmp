package io.shortway.kobankat.models

import cocoapods.RevenueCat.RCStoreTransaction
import io.shortway.kobankat.ktx.toEpochMilliseconds

public actual typealias StoreTransaction = RCStoreTransaction

public actual val StoreTransaction.transactionId: String?
    get() = transactionIdentifier()

public actual val StoreTransaction.productIds: List<String>
    get() = listOf(productIdentifier())

public actual val StoreTransaction.purchaseTime: Long
    get() = purchaseDate().toEpochMilliseconds()