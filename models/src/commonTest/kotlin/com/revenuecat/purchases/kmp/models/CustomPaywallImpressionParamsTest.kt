package com.revenuecat.purchases.kmp.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CustomPaywallImpressionParamsTest {

    @Test
    fun `default constructor has null paywallId`() {
        val params = CustomPaywallImpressionParams()
        assertNotNull(params)
        assertNull(params.paywallId)
    }

    @Test
    fun `constructor with paywallId`() {
        val params = CustomPaywallImpressionParams(paywallId = "my-paywall")
        assertNotNull(params)
        assertEquals("my-paywall", params.paywallId)
    }

    @Test
    fun `constructor with null paywallId`() {
        val params = CustomPaywallImpressionParams(paywallId = null)
        assertNotNull(params)
        assertNull(params.paywallId)
    }
}
