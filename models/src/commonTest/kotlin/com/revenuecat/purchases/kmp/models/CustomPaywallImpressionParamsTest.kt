package com.revenuecat.purchases.kmp.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CustomPaywallImpressionParamsTest {

    @Test
    fun `default constructor has null paywallId and offeringId`() {
        val params = CustomPaywallImpressionParams()
        assertNotNull(params)
        assertNull(params.paywallId)
        assertNull(params.offeringId)
    }

    @Test
    fun `constructor with paywallId`() {
        val params = CustomPaywallImpressionParams(paywallId = "my-paywall")
        assertNotNull(params)
        assertEquals("my-paywall", params.paywallId)
        assertNull(params.offeringId)
    }

    @Test
    fun `constructor with null paywallId`() {
        val params = CustomPaywallImpressionParams(paywallId = null)
        assertNotNull(params)
        assertNull(params.paywallId)
    }

    @Test
    fun `constructor with offeringId only`() {
        val params = CustomPaywallImpressionParams(offeringId = "my-offering")
        assertNotNull(params)
        assertNull(params.paywallId)
        assertEquals("my-offering", params.offeringId)
    }

    @Test
    fun `constructor with both paywallId and offeringId`() {
        val params = CustomPaywallImpressionParams(
            paywallId = "my-paywall",
            offeringId = "my-offering",
        )
        assertNotNull(params)
        assertEquals("my-paywall", params.paywallId)
        assertEquals("my-offering", params.offeringId)
    }
}
