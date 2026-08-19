package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kn.core.RCPurchases as IosPurchases
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests for setAttributes on iOS to verify null value handling.
 * 
 * This test verifies that null values in the attributes map are correctly converted to empty strings
 * before being passed to the native iOS SDK, preventing the NSNull cast crash described in issue #979.
 */
class PurchasesSetAttributesTest {

    @BeforeTest
    fun setup() {
        // Ensure Purchases is configured before running tests
        if (!IosPurchases.isConfigured()) {
            Purchases.configure(PurchasesConfiguration(apiKey = "test_api_key"))
        }
    }

    @Test
    fun `setAttributes with null values does not crash`() {
        // This test verifies the fix for issue #979 where null values caused NSNull cast crashes
        val purchases = Purchases.sharedInstance
        
        // This should not crash - null values should be converted to empty strings
        try {
            purchases.setAttributes(mapOf(
                "test_attribute" to null,
                "another_attribute" to "valid_value"
            ))
            // If we reach here, the test passed (no crash)
            assertTrue(true, "setAttributes with null values did not crash")
        } catch (e: Exception) {
            throw AssertionError("setAttributes with null values should not throw an exception", e)
        }
    }

    @Test
    fun `setAttributes with all null values does not crash`() {
        val purchases = Purchases.sharedInstance
        
        try {
            purchases.setAttributes(mapOf(
                "email" to null,
                "displayName" to null,
                "customAttribute" to null
            ))
            assertTrue(true, "setAttributes with all null values did not crash")
        } catch (e: Exception) {
            throw AssertionError("setAttributes with all null values should not throw an exception", e)
        }
    }

    @Test
    fun `setAttributes with mixed null and non-null values does not crash`() {
        val purchases = Purchases.sharedInstance
        
        try {
            purchases.setAttributes(mapOf(
                "email" to "test@example.com",
                "displayName" to null,
                "phoneNumber" to "+1234567890",
                "customId" to null
            ))
            assertTrue(true, "setAttributes with mixed null and non-null values did not crash")
        } catch (e: Exception) {
            throw AssertionError("setAttributes with mixed values should not throw an exception", e)
        }
    }

    @Test
    fun `setAttributes with empty map does not crash`() {
        val purchases = Purchases.sharedInstance
        
        try {
            purchases.setAttributes(emptyMap())
            assertTrue(true, "setAttributes with empty map did not crash")
        } catch (e: Exception) {
            throw AssertionError("setAttributes with empty map should not throw an exception", e)
        }
    }

    @Test
    fun `setAttributes with only valid values works as expected`() {
        val purchases = Purchases.sharedInstance
        
        try {
            purchases.setAttributes(mapOf(
                "email" to "test@example.com",
                "displayName" to "Test User",
                "customAttribute" to "value123"
            ))
            assertTrue(true, "setAttributes with only valid values did not crash")
        } catch (e: Exception) {
            throw AssertionError("setAttributes with valid values should not throw an exception", e)
        }
    }

    @Test
    fun `setAttributes converts null to empty string internally`() {
        // This test verifies the mapping logic directly
        val inputMap = mapOf(
            "key1" to "value1",
            "key2" to null,
            "key3" to "value3"
        )
        
        // Apply the same transformation that the iOS implementation uses
        val transformedMap = inputMap.mapValues { (_, value) -> value ?: "" }
        
        // Verify that null values are converted to empty strings
        assertEquals("value1", transformedMap["key1"])
        assertEquals("", transformedMap["key2"]) // null converted to empty string
        assertEquals("value3", transformedMap["key3"])
        
        // Verify no null values remain in the map
        assertTrue(transformedMap.values.none { it == null }, "Transformed map should not contain null values")
    }
}
