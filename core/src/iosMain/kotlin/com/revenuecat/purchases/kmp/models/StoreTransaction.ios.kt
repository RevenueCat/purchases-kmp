package com.revenuecat.purchases.kmp.models
//
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
//
