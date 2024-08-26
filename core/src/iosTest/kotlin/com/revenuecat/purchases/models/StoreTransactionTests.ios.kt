package com.revenuecat.purchases.models


import com.revenuecat.purchases.kmp.models.StoreTransaction
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StoreTransactionTests {

    @Test
    fun `fromMap correctly parses map with all fields`() {
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

        val result = StoreTransaction.fromMap(storeTransactionMap = hybridMap)

        assertTrue(result.isSuccess)
        val storeTransaction = result.getOrThrow()
        assertEquals(transactionId, storeTransaction.transactionId)
        assertEquals(listOf(productId), storeTransaction.productIds)
        assertEquals(purchaseDateMillis.toLong(), storeTransaction.purchaseTime)
    }

    @Test
    fun `fromMap correctly parses map with all fields if purchaseDateMillis is an int`() {
        val transactionId = "someTransactionIdentifier"
        val productId = "someProductIdentifier"
        val purchaseDateMillis = 1627689600000
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "transactionIdentifier" to transactionId,
            "revenueCatId" to transactionId,    // Deprecated
            "productIdentifier" to productId,
            "productId" to productId,   // Deprecated
            "purchaseDateMillis" to purchaseDateMillis,
            "purchaseDate" to purchaseDate
        )

        val result = StoreTransaction.fromMap(storeTransactionMap = hybridMap)

        assertTrue(result.isSuccess)
        val storeTransaction = result.getOrThrow()
        assertEquals(transactionId, storeTransaction.transactionId)
        assertEquals(listOf(productId), storeTransaction.productIds)
        assertEquals(purchaseDateMillis.toLong(), storeTransaction.purchaseTime)
    }

    @Test
    fun `missing transactionIdentifier returns failure`() {
        val productId = "someProductIdentifier"
        val purchaseDateMillis = 1627689600000.0
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "productIdentifier" to productId,
            "purchaseDateMillis" to purchaseDateMillis,
            "purchaseDate" to purchaseDate
        )

        val result = StoreTransaction.fromMap(storeTransactionMap = hybridMap)

        assertTrue(result.isFailure)
        result.exceptionOrNull()?.let {
            assertTrue(it is IllegalArgumentException)
            assertEquals("Expected a non-null transactionIdentifier", it.message)
        }
    }

    @Test
    fun `missing productIdentifier returns failure`() {
        val transactionId = "someTransactionIdentifier"
        val purchaseDateMillis = 1627689600000.0
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "transactionIdentifier" to transactionId,
            "purchaseDateMillis" to purchaseDateMillis,
            "purchaseDate" to purchaseDate
        )

        val result = StoreTransaction.fromMap(storeTransactionMap = hybridMap)

        assertTrue(result.isFailure)
        result.exceptionOrNull()?.let {
            assertTrue(it is IllegalArgumentException)
            assertEquals("Expected a non-null productIdentifier", it.message)
        }
    }

    @Test
    fun `missing purchaseDateMillis returns failure`() {
        val transactionId = "someTransactionIdentifier"
        val productId = "someProductIdentifier"
        val purchaseDate = "2021-07-31T00:00:00Z"

        val hybridMap: Map<Any?, *> = mapOf(
            "transactionIdentifier" to transactionId,
            "productIdentifier" to productId,
            "purchaseDateMillis" to "InvalidMillis",  // This should be a Double
            "purchaseDate" to purchaseDate
        )

        val result = StoreTransaction.fromMap(storeTransactionMap = hybridMap)

        assertTrue(result.isFailure)
        result.exceptionOrNull()?.let {
            assertTrue(it is IllegalArgumentException)
            assertEquals("Expected a non-null purchaseDateMillis", it.message)
        }
    }
}
