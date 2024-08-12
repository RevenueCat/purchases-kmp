@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.StoreTransaction as RcStoreTransaction

internal fun StoreTransaction(
    rcStoreTransaction: RcStoreTransaction
): StoreTransaction {
    // TODO: Test that this works as intended
    return StoreTransaction(
        transactionId = rcStoreTransaction.orderId ?: error("Expect a non-null orderId."),
        productIDs = rcStoreTransaction.productIds,
        purchaseTime = rcStoreTransaction.purchaseTime
    )
}