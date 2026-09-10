package com.revenuecat.purchases.kmp.ui.revenuecatui

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PaywallInteractionEventTest {

    private val event = PaywallInteractionEvent(
        mapOf(
            "timestamp" to 1_700_000_000_000L,
            "paywall_revision" to 3,
            "dark_mode" to true,
            "component_type" to PaywallInteractionEvent.ComponentTypes.TAB,
        ),
    )

    @Test
    fun `typed keys read their values`() {
        assertEquals(1_700_000_000_000L, event.getProperty(PaywallInteractionEvent.Keys.TIMESTAMP))
        assertEquals(3, event.getProperty(PaywallInteractionEvent.Keys.PAYWALL_REVISION))
        assertEquals(true, event.getProperty(PaywallInteractionEvent.Keys.DARK_MODE))
        assertEquals("tab", event.getProperty(PaywallInteractionEvent.Keys.COMPONENT_TYPE))
    }

    @Test
    fun `absent keys are null`() {
        assertNull(event.getProperty(PaywallInteractionEvent.Keys.COMPONENT_URL))
    }

    @Test
    fun `integer keys accept any numeric width`() {
        val wide = PaywallInteractionEvent(mapOf("paywall_revision" to 3L, "timestamp" to 5))
        assertEquals(3, wide.getProperty(PaywallInteractionEvent.Keys.PAYWALL_REVISION))
        assertEquals(5L, wide.getProperty(PaywallInteractionEvent.Keys.TIMESTAMP))
    }

    @Test
    fun `mismatched types are null`() {
        val wrong = PaywallInteractionEvent(mapOf("component_type" to 1, "dark_mode" to "yes"))
        assertNull(wrong.getProperty(PaywallInteractionEvent.Keys.COMPONENT_TYPE))
        assertNull(wrong.getProperty(PaywallInteractionEvent.Keys.DARK_MODE))
    }
}
