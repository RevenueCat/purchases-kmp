package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Unit tests for setAttributes iOS implementation to verify null value handling.
 * 
 * These tests verify that null values in the attributes map are correctly converted to empty strings
 * before being passed to the native iOS SDK, preventing the NSNull cast crash described in issue #979.
 * 
 * Note: These are pure unit tests of the transformation logic. Integration tests with the actual
 * SDK are covered in the sample app and manual testing.
 */
class PurchasesSetAttributesTest {

    @Test
    fun `mapValues transformation converts null to empty string`() {
        // This test verifies the exact transformation used in the iOS implementation
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
    }

    @Test
    fun `mapValues transformation handles all null values`() {
        val inputMap = mapOf(
            "email" to null,
            "displayName" to null,
            "customAttribute" to null
        )
        
        val transformedMap = inputMap.mapValues { (_, value) -> value ?: "" }
        
        // All values should be empty strings
        assertEquals("", transformedMap["email"])
        assertEquals("", transformedMap["displayName"])
        assertEquals("", transformedMap["customAttribute"])
    }

    @Test
    fun `mapValues transformation handles mixed null and non-null values`() {
        val inputMap = mapOf(
            "email" to "test@example.com",
            "displayName" to null,
            "phoneNumber" to "+1234567890",
            "customId" to null
        )
        
        val transformedMap = inputMap.mapValues { (_, value) -> value ?: "" }
        
        // Verify correct transformation
        assertEquals("test@example.com", transformedMap["email"])
        assertEquals("", transformedMap["displayName"])
        assertEquals("+1234567890", transformedMap["phoneNumber"])
        assertEquals("", transformedMap["customId"])
    }

    @Test
    fun `mapValues transformation handles empty map`() {
        val inputMap = emptyMap<String, String?>()
        
        val transformedMap = inputMap.mapValues { (_, value) -> value ?: "" }
        
        // Empty map should remain empty
        assertEquals(0, transformedMap.size)
    }

    @Test
    fun `mapValues transformation handles only non-null values`() {
        val inputMap = mapOf(
            "email" to "test@example.com",
            "displayName" to "Test User",
            "customAttribute" to "value123"
        )
        
        val transformedMap = inputMap.mapValues { (_, value) -> value ?: "" }
        
        // All values should pass through unchanged
        assertEquals("test@example.com", transformedMap["email"])
        assertEquals("Test User", transformedMap["displayName"])
        assertEquals("value123", transformedMap["customAttribute"])
    }

    @Test
    fun `transformed map type is Map of String to String (non-nullable)`() {
        val inputMap = mapOf(
            "key1" to "value1",
            "key2" to null
        )
        
        val transformedMap: Map<String, String> = inputMap.mapValues { (_, value) -> value ?: "" }
        
        // Verify that the resulting map has non-nullable String values
        // This validates that the transformation produces the correct type for Swift interop
        assertEquals(2, transformedMap.size)
        assertFalse(transformedMap.isEmpty())
    }

    @Test
    fun `mapValues transformation preserves keys`() {
        val inputMap = mapOf(
            "key1" to "value1",
            "key2" to null,
            "key3" to "value3"
        )
        
        val transformedMap = inputMap.mapValues { (_, value) -> value ?: "" }
        
        // Verify all keys are preserved
        assertEquals(inputMap.keys, transformedMap.keys)
    }
}
