package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for the iOS [setAttributes] mapping used by [Purchases.setAttributes].
 *
 * These call the production helper so a revert of the iOS actual would fail CI. Calling the
 * native SDK from unit tests is not viable here: it requires a configured Purchases instance
 * and previously segfaulted in the simulator.
 */
class PurchasesSetAttributesTest {

    @Test
    fun `toIosSubscriberAttributes converts null values to empty strings`() {
        val mapped = mapOf(
            "key1" to "value1",
            "key2" to null,
            "key3" to "value3"
        ).toIosSubscriberAttributes()

        assertEquals<Map<*, *>>(
            mapOf(
                "key1" to "value1",
                "key2" to "",
                "key3" to "value3"
            ),
            mapped
        )
    }

    @Test
    fun `toIosSubscriberAttributes converts a map of only nulls to empty strings`() {
        val mapped = mapOf(
            "email" to null,
            "displayName" to null,
            "customAttribute" to null
        ).toIosSubscriberAttributes()

        assertEquals<Map<*, *>>(
            mapOf(
                "email" to "",
                "displayName" to "",
                "customAttribute" to ""
            ),
            mapped
        )
    }

    @Test
    fun `toIosSubscriberAttributes leaves non-null values unchanged`() {
        val mapped = mapOf(
            "email" to "test@example.com",
            "displayName" to "Test User",
            "customAttribute" to "value123"
        ).toIosSubscriberAttributes()

        assertEquals<Map<*, *>>(
            mapOf(
                "email" to "test@example.com",
                "displayName" to "Test User",
                "customAttribute" to "value123"
            ),
            mapped
        )
    }

    @Test
    fun `toIosSubscriberAttributes leaves an empty map empty`() {
        assertEquals<Map<*, *>>(
            emptyMap<String, String>(),
            emptyMap<String, String?>().toIosSubscriberAttributes()
        )
    }
}
