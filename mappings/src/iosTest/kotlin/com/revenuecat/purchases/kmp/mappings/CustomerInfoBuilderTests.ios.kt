package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kmp.models.PeriodType
import com.revenuecat.purchases.kmp.models.Store
import com.revenuecat.purchases.kmp.models.VerificationResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CustomerInfoBuilderTests {

    @Test
    fun `buildCustomerInfo correctly parses a full PHC dictionary`() {
        val map: Map<Any?, *> = createFullCustomerInfoMap()

        val result = buildCustomerInfo(map)

        assertTrue(result.isSuccess)
        val customerInfo = result.getOrThrow()
        assertEquals(setOf("com.test.monthly"), customerInfo.activeSubscriptions)
        assertEquals(setOf("com.test.monthly"), customerInfo.allPurchasedProductIdentifiers)
        assertEquals("user123", customerInfo.originalAppUserId)
        assertEquals(1700000000000L, customerInfo.firstSeenMillis)
        assertEquals(1710000000000L, customerInfo.requestDateMillis)
        assertEquals(1720000000000L, customerInfo.latestExpirationDateMillis)
        assertEquals("https://apps.apple.com/manage", customerInfo.managementUrlString)
        assertNull(customerInfo.originalApplicationVersion)
        assertEquals(1690000000000L, customerInfo.originalPurchaseDateMillis)
    }

    @Test
    fun `buildCustomerInfo parses entitlements correctly`() {
        val map: Map<Any?, *> = createFullCustomerInfoMap()

        val customerInfo = buildCustomerInfo(map).getOrThrow()

        assertEquals(1, customerInfo.entitlements.all.size)
        val entitlement = customerInfo.entitlements["premium"]
        assertNotNull(entitlement)
        assertEquals("premium", entitlement.identifier)
        assertTrue(entitlement.isActive)
        assertTrue(entitlement.willRenew)
        assertEquals(PeriodType.NORMAL, entitlement.periodType)
        assertEquals(Store.APP_STORE, entitlement.store)
        assertEquals("com.test.monthly", entitlement.productIdentifier)
        assertTrue(entitlement.isSandbox)
        assertEquals(OwnershipType.PURCHASED, entitlement.ownershipType)
        assertEquals(VerificationResult.VERIFIED, entitlement.verification)
        assertEquals(1705000000000L, entitlement.latestPurchaseDateMillis)
        assertEquals(1700000000000L, entitlement.originalPurchaseDateMillis)
        assertEquals(1720000000000L, entitlement.expirationDateMillis)
    }

    @Test
    fun `buildCustomerInfo parses entitlements verification`() {
        val map: Map<Any?, *> = createFullCustomerInfoMap()

        val customerInfo = buildCustomerInfo(map).getOrThrow()

        assertEquals(VerificationResult.VERIFIED, customerInfo.entitlements.verification)
    }

    @Test
    fun `buildCustomerInfo parses expiration and purchase date maps`() {
        val map: Map<Any?, *> = createFullCustomerInfoMap()

        val customerInfo = buildCustomerInfo(map).getOrThrow()

        assertEquals(1720000000000L, customerInfo.allExpirationDateMillis["com.test.monthly"])
        assertEquals(1705000000000L, customerInfo.allPurchaseDateMillis["com.test.monthly"])
    }

    @Test
    fun `buildCustomerInfo parses non-subscription transactions`() {
        val map: Map<Any?, *> = createFullCustomerInfoMap()

        val customerInfo = buildCustomerInfo(map).getOrThrow()

        assertEquals(1, customerInfo.nonSubscriptionTransactions.size)
        val transaction = customerInfo.nonSubscriptionTransactions.first()
        assertEquals("txn_001", transaction.transactionIdentifier)
        assertEquals("com.test.consumable", transaction.productIdentifier)
        assertEquals(1706000000000L, transaction.purchaseDateMillis)
    }

    @Test
    fun `buildCustomerInfo handles empty map with required fields only`() {
        val map: Map<Any?, *> = mapOf(
            "originalAppUserId" to "user_minimal",
            "firstSeenMillis" to 1700000000000.0,
            "requestDateMillis" to 1710000000000.0,
        )

        val result = buildCustomerInfo(map)

        assertTrue(result.isSuccess)
        val customerInfo = result.getOrThrow()
        assertEquals("user_minimal", customerInfo.originalAppUserId)
        assertEquals(emptySet(), customerInfo.activeSubscriptions)
        assertEquals(emptyMap(), customerInfo.allExpirationDateMillis)
        assertEquals(0, customerInfo.entitlements.all.size)
        assertEquals(emptyList(), customerInfo.nonSubscriptionTransactions)
    }

    @Test
    fun `buildCustomerInfo fails when missing originalAppUserId`() {
        val map: Map<Any?, *> = mapOf(
            "firstSeenMillis" to 1700000000000.0,
            "requestDateMillis" to 1710000000000.0,
        )

        val result = buildCustomerInfo(map)

        assertTrue(result.isFailure)
    }

    @Test
    fun `buildCustomerInfo fails when missing firstSeenMillis`() {
        val map: Map<Any?, *> = mapOf(
            "originalAppUserId" to "user123",
            "requestDateMillis" to 1710000000000.0,
        )

        val result = buildCustomerInfo(map)

        assertTrue(result.isFailure)
    }

    @Test
    fun `buildCustomerInfo fails when missing requestDateMillis`() {
        val map: Map<Any?, *> = mapOf(
            "originalAppUserId" to "user123",
            "firstSeenMillis" to 1700000000000.0,
        )

        val result = buildCustomerInfo(map)

        assertTrue(result.isFailure)
    }

    @Test
    fun `parseStore maps all known store strings`() {
        assertEquals(Store.APP_STORE, parseStore("APP_STORE"))
        assertEquals(Store.MAC_APP_STORE, parseStore("MAC_APP_STORE"))
        assertEquals(Store.PLAY_STORE, parseStore("PLAY_STORE"))
        assertEquals(Store.STRIPE, parseStore("STRIPE"))
        assertEquals(Store.PROMOTIONAL, parseStore("PROMOTIONAL"))
        assertEquals(Store.AMAZON, parseStore("AMAZON"))
        assertEquals(Store.RC_BILLING, parseStore("RC_BILLING"))
        assertEquals(Store.EXTERNAL, parseStore("EXTERNAL"))
        assertEquals(Store.PADDLE, parseStore("PADDLE"))
        assertEquals(Store.TEST_STORE, parseStore("TEST_STORE"))
        assertEquals(Store.GALAXY, parseStore("GALAXY"))
        assertEquals(Store.UNKNOWN_STORE, parseStore("SOMETHING_ELSE"))
        assertEquals(Store.UNKNOWN_STORE, parseStore(null))
    }

    @Test
    fun `parsePeriodType maps all known period type strings`() {
        assertEquals(PeriodType.NORMAL, parsePeriodType("NORMAL"))
        assertEquals(PeriodType.INTRO, parsePeriodType("INTRO"))
        assertEquals(PeriodType.TRIAL, parsePeriodType("TRIAL"))
        assertEquals(PeriodType.PREPAID, parsePeriodType("PREPAID"))
        assertEquals(PeriodType.NORMAL, parsePeriodType(null))
    }

    @Test
    fun `parseOwnershipType maps all known ownership type strings`() {
        assertEquals(OwnershipType.PURCHASED, parseOwnershipType("PURCHASED"))
        assertEquals(OwnershipType.FAMILY_SHARED, parseOwnershipType("FAMILY_SHARED"))
        assertEquals(OwnershipType.UNKNOWN, parseOwnershipType("SOMETHING_ELSE"))
        assertEquals(OwnershipType.UNKNOWN, parseOwnershipType(null))
    }

    @Test
    fun `parseVerificationResult maps all known verification result strings`() {
        assertEquals(VerificationResult.VERIFIED, parseVerificationResult("VERIFIED"))
        assertEquals(VerificationResult.VERIFIED_ON_DEVICE, parseVerificationResult("VERIFIED_ON_DEVICE"))
        assertEquals(VerificationResult.FAILED, parseVerificationResult("FAILED"))
        assertEquals(VerificationResult.NOT_REQUESTED, parseVerificationResult("NOT_REQUESTED"))
        assertEquals(VerificationResult.NOT_REQUESTED, parseVerificationResult(null))
    }

    private fun createFullCustomerInfoMap(): Map<Any?, *> = mapOf(
        "activeSubscriptions" to listOf("com.test.monthly"),
        "allPurchasedProductIdentifiers" to listOf("com.test.monthly"),
        "allExpirationDatesMillis" to mapOf("com.test.monthly" to 1720000000000.0),
        "allPurchaseDatesMillis" to mapOf("com.test.monthly" to 1705000000000.0),
        "entitlements" to mapOf(
            "all" to mapOf(
                "premium" to mapOf(
                    "identifier" to "premium",
                    "isActive" to true,
                    "willRenew" to true,
                    "periodType" to "NORMAL",
                    "latestPurchaseDateMillis" to 1705000000000.0,
                    "originalPurchaseDateMillis" to 1700000000000.0,
                    "expirationDateMillis" to 1720000000000.0,
                    "store" to "APP_STORE",
                    "productIdentifier" to "com.test.monthly",
                    "productPlanIdentifier" to null,
                    "isSandbox" to true,
                    "unsubscribeDetectedAtMillis" to null,
                    "billingIssueDetectedAtMillis" to null,
                    "ownershipType" to "PURCHASED",
                    "verification" to "VERIFIED",
                ),
            ),
            "verification" to "VERIFIED",
        ),
        "firstSeenMillis" to 1700000000000.0,
        "latestExpirationDateMillis" to 1720000000000.0,
        "requestDateMillis" to 1710000000000.0,
        "originalAppUserId" to "user123",
        "originalApplicationVersion" to null,
        "originalPurchaseDateMillis" to 1690000000000.0,
        "managementURL" to "https://apps.apple.com/manage",
        "nonSubscriptionTransactions" to listOf(
            mapOf(
                "transactionIdentifier" to "txn_001",
                "productIdentifier" to "com.test.consumable",
                "purchaseDateMillis" to 1706000000000.0,
            ),
        ),
        "subscriptionsByProductIdentifier" to emptyMap<String, Any>(),
    )
}
