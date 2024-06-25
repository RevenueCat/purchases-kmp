package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ktx.toEpochMilliseconds
import platform.Foundation.NSDate

// FIXME This should be a typealias of RCNonSubscriptionTransaction so we don't need to use a map.
public actual class Transaction internal constructor(map: Map<String, Any?>) {
    internal val transactionIdentifier: String by map
    internal val productIdentifier: String by map
    internal val purchaseDate: NSDate by map
}

public actual val Transaction.transactionIdentifier: String
    get() = transactionIdentifier
public actual val Transaction.productIdentifier: String
    get() = productIdentifier
public actual val Transaction.purchaseDateMillis: Long
    get() = purchaseDate.toEpochMilliseconds()
