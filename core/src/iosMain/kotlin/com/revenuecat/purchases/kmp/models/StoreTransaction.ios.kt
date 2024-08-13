package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCStoreTransaction
import com.revenuecat.purchases.kmp.ktx.toEpochMilliseconds
import platform.Foundation.NSDate

public actual class StoreTransaction private constructor(
    internal val transactionId: String?,
    internal val productIds: List<String>,
    internal val purchaseTime: Long
) {

    internal constructor(
        rcTransaction: RCStoreTransaction
    ) : this(
        transactionId = rcTransaction.transactionIdentifier(),
        productIds = listOf(rcTransaction.productIdentifier()),
        purchaseTime = rcTransaction.purchaseDate().toEpochMilliseconds()
    )

    internal constructor(
        transactionId: String,
        productId: String,
        purchaseDate: NSDate,
    ) : this(
        transactionId = transactionId,
        productIds = listOf(productId),
        purchaseTime = purchaseDate.toEpochMilliseconds()
    )

}

public actual val StoreTransaction.transactionId: String?
    get() = transactionId

public actual val StoreTransaction.productIds: List<String>
    get() = productIds

public actual val StoreTransaction.purchaseTime: Long
    get() = purchaseTime
