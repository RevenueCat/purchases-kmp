package com.revenuecat.purchases.models


import com.revenuecat.purchases.kmp.models.StoreTransaction
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StoreTransactionTests {

    @Test
    fun testFromMapCorrectlyParsesMapWithAllFields() {
        val transactionId = "someTransactionIdentifier"
        val productId = "someProductIdentifier"
        val purchaseDateMillis = 1627689600000.0
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "transactionIdentifier" to transactionId,
            "revenueCatId" to transactionId,    // Deprecated
            "productIdentifier" to productId,
            "productId" to productId,   // Deprecated
            "purchaseDateMillis" to purchaseDateMillis,
            "purchaseDate" to purchaseDate
        )

        val storeTransaction = StoreTransaction.fromMap(storeTransactionMap = hybridMap)

        assertEquals(transactionId, storeTransaction.transactionId)
        assertEquals(listOf(productId), storeTransaction.productIds)
        assertEquals(purchaseDateMillis.toLong(), storeTransaction.purchaseTime)
    }

    @Test
    fun testMissingTransactionIdentifier() {
        val productId = "someProductIdentifier"
        val purchaseDateMillis = 1627689600000.0
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "productIdentifier" to productId,
            "purchaseDateMillis" to purchaseDateMillis,
            "purchaseDate" to purchaseDate
        )

        assertFailsWith<IllegalArgumentException>("Expected a non-null transactionIdentifier") {
            StoreTransaction.fromMap(storeTransactionMap = hybridMap)
        }
    }

    @Test
    fun testMissingProductIdentifier() {
        val transactionId = "someTransactionIdentifier"
        val purchaseDateMillis = 1627689600000.0
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "transactionIdentifier" to transactionId,
            "purchaseDateMillis" to purchaseDateMillis,
            "purchaseDate" to purchaseDate
        )

        assertFailsWith<IllegalArgumentException>("Expected a non-null productIdentifier") {
            StoreTransaction.fromMap(storeTransactionMap = hybridMap)
        }
    }

    @Test
    fun testInvalidPurchaseDateMillis() {
        val transactionId = "someTransactionIdentifier"
        val productId = "someProductIdentifier"
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "transactionIdentifier" to transactionId,
            "productIdentifier" to productId,
            "purchaseDateMillis" to "InvalidMillis",  // This should be a Double
            "purchaseDate" to purchaseDate
        )

        assertFailsWith<IllegalArgumentException>("Expected a non-null purchaseDateMillis") {
            StoreTransaction.fromMap(storeTransactionMap = hybridMap)
        }
    }
}