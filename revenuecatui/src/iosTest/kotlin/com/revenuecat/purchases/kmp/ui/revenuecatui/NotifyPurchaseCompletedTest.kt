package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.EntitlementInfos
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.VerificationResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class NotifyPurchaseCompletedTest {

    private val completions = mutableListOf<Pair<CustomerInfo, StoreTransaction>>()
    private val listener = object : PaywallListener {
        override fun onPurchaseCompleted(customerInfo: CustomerInfo, storeTransaction: StoreTransaction) {
            completions += customerInfo to storeTransaction
        }
    }

    @Test
    fun `purchase without a transaction does not call onPurchaseCompleted`() {
        listener.notifyPurchaseCompleted(
            customerInfo = { error("customerInfo must not be mapped") },
            storeTransaction = null,
        )

        assertTrue(completions.isEmpty())
    }

    @Test
    fun `purchase with a transaction calls onPurchaseCompleted`() {
        val customerInfo = customerInfo()
        val storeTransaction = StoreTransaction(
            transactionId = "transaction",
            productIds = listOf("monthly"),
            purchaseTime = 1_700_000_000_000L,
        )

        listener.notifyPurchaseCompleted(customerInfo = { customerInfo }, storeTransaction = storeTransaction)

        assertEquals(1, completions.size)
        assertSame(customerInfo, completions.single().first)
        assertSame(storeTransaction, completions.single().second)
    }

    private fun customerInfo() = CustomerInfo(
        activeSubscriptions = emptySet(),
        allExpirationDateMillis = emptyMap(),
        allPurchaseDateMillis = emptyMap(),
        allPurchasedProductIdentifiers = emptySet(),
        entitlements = EntitlementInfos(all = emptyMap(), verification = VerificationResult.NOT_REQUESTED),
        firstSeenMillis = 0L,
        latestExpirationDateMillis = null,
        managementUrlString = null,
        subscriptionsByProductIdentifier = emptyMap(),
        nonSubscriptionTransactions = emptyList(),
        originalAppUserId = "user",
        originalApplicationVersion = null,
        originalPurchaseDateMillis = null,
        requestDateMillis = 0L,
    )
}
