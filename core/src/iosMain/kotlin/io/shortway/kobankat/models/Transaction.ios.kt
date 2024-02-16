package io.shortway.kobankat.models

import cocoapods.RevenueCat.RCNonSubscriptionTransaction
import io.shortway.kobankat.ktx.toEpochMilliseconds

public actual typealias Transaction = RCNonSubscriptionTransaction

public actual val Transaction.transactionIdentifier: String
    get() = transactionIdentifier()
public actual val Transaction.productIdentifier: String
    get() = productIdentifier()
public actual val Transaction.purchaseDateMillis: Long
    get() = purchaseDate().toEpochMilliseconds()