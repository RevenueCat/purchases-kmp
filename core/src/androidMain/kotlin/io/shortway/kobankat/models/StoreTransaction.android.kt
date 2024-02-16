@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat.models

import com.revenuecat.purchases.models.PurchaseType as RcPurchaseType
import com.revenuecat.purchases.models.StoreTransaction as RcStoreTransaction

public actual typealias StoreTransaction = RcStoreTransaction

public actual val StoreTransaction.transactionId: String?
    get() = orderId

public actual val StoreTransaction.productIds: List<String>
    get() = productIds

public actual val StoreTransaction.purchaseTime: Long
    get() = purchaseTime
