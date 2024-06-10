package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreTransaction
import com.revenuecat.purchases.kmp.ktx.toEpochMilliseconds

public actual typealias Transaction = RCStoreTransaction

public actual val Transaction.transactionIdentifier: String
    get() = transactionIdentifier()
public actual val Transaction.productIdentifier: String
    get() = productIdentifier()
public actual val Transaction.purchaseDateMillis: Long
    get() = purchaseDate().toEpochMilliseconds()
