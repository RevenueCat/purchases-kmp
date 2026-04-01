package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.StoreTransaction
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreTransaction as IosStoreTransaction

public fun IosStoreTransaction.toStoreTransaction(): StoreTransaction =
    StoreTransaction(
        transactionId = transactionIdentifier(),
        productIds = listOf(productIdentifier()),
        purchaseTime = purchaseDate().toEpochMilliseconds(),
    )
