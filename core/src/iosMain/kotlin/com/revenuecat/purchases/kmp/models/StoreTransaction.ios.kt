package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreTransaction
import platform.Foundation.timeIntervalSince1970

internal fun StoreTransaction(
    rcStoreTransaction: RCStoreTransaction
): StoreTransaction {
    // TODO: Test that this works as intended
    return StoreTransaction(
        transactionId = rcStoreTransaction.transactionIdentifier(),
        productIDs = rcStoreTransaction.productIdentifier().map { it as String },
        purchaseTime = rcStoreTransaction.purchaseDate().timeIntervalSince1970().toLong()
    )
}