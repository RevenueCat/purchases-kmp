@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models
//
//import com.revenuecat.purchases.models.StoreTransaction as RcStoreTransaction
//
//public actual typealias StoreTransaction = RcStoreTransaction
//
//public actual val StoreTransaction.transactionId: String?
//    get() = orderId
//
//public actual val StoreTransaction.productIds: List<String>
//    get() = productIds
//
//public actual val StoreTransaction.purchaseTime: Long
//    get() = purchaseTime

//public actual class StoreTransaction(
//    public actual val transactionId: String,
//    public actual val productIDs: List<String>,
//    public actual val purchaseTime: Long
//) {
//    public constructor(map: Map<String, Any>) : this(
//        transactionId = map["transactionIdentifier"] as? String
//            ?: throw IllegalArgumentException("transactionIdentifier is missing or not a String"),
//        productIDs = map["productIdentifier"]?.let { listOf(it as String) }
//            ?: throw IllegalArgumentException("productIdentifier is missing or not a String"),
//        purchaseTime = map["purchaseDateMillis"] as? Long
//            ?: throw IllegalArgumentException("purchaseDateMillis is missing or not a Long")
//    )
//}