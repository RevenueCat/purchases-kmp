package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.models.Transaction as AndroidTransaction
import com.revenuecat.purchases.kmp.models.Transaction

internal fun AndroidTransaction.toTransaction(): Transaction {
    return Transaction(
        transactionIdentifier = transactionIdentifier,
        productIdentifier = productIdentifier,
        purchaseDateMillis = purchaseDate.time
    )
}
