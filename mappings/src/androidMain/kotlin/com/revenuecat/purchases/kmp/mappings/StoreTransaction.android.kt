package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.models.StoreTransaction as AndroidStoreTransaction

public fun AndroidStoreTransaction.toStoreTransaction(): StoreTransaction =
    StoreTransaction(
        transactionId = orderId,
        productIds = productIds,
        purchaseTime = purchaseTime
    )
