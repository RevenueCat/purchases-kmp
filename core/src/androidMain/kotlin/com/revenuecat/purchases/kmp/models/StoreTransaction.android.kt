@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.StoreTransaction as RcStoreTransaction

public actual typealias StoreTransaction = RcStoreTransaction

public actual val StoreTransaction.transactionId: String?
    get() = orderId

public actual val StoreTransaction.productIds: List<String>
    get() = productIds

public actual val StoreTransaction.purchaseTime: Long
    get() = purchaseTime
