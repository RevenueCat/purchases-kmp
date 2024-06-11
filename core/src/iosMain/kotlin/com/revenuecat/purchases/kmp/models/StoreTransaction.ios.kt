package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreTransaction
import com.revenuecat.purchases.kmp.ktx.toEpochMilliseconds

public actual typealias StoreTransaction = RCStoreTransaction

public actual val StoreTransaction.transactionId: String?
    get() = transactionIdentifier()

public actual val StoreTransaction.productIds: List<String>
    get() = listOf(productIdentifier())

public actual val StoreTransaction.purchaseTime: Long
    get() = purchaseDate().toEpochMilliseconds()
