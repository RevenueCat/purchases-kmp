@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.Transaction as RcTransaction

public actual typealias Transaction = RcTransaction

public actual val Transaction.transactionIdentifier: String
    get() = transactionIdentifier
public actual val Transaction.productIdentifier: String
    get() = productIdentifier
public actual val Transaction.purchaseDateMillis: Long
    get() = purchaseDate.time
