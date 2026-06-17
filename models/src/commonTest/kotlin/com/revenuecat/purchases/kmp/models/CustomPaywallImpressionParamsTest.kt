package com.revenuecat.purchases.kmp.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Suppress("DEPRECATION")
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
        assertNull(params.offeringId)
    }

    @Test
    fun `constructor with null offeringId`() {
        val params = CustomPaywallImpressionParams(offeringId = null)
        assertNotNull(params)
        assertNull(params.paywallId)
        assertNull(params.offeringId)
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

    @Test
    fun `constructor preserves empty paywallId and offeringId`() {
        val params = CustomPaywallImpressionParams(
            paywallId = "",
            offeringId = "",
        )

        assertNotNull(params)
        assertEquals("", params.paywallId)
        assertEquals("", params.offeringId)
    }

    @Test
    fun `constructor with offering populates offeringId from offering`() {
        val offering = FakeOffering(identifier = "my-offering")
        val params = CustomPaywallImpressionParams(
            paywallId = "my-paywall",
            offering = offering,
        )

        assertNotNull(params)
        assertEquals("my-paywall", params.paywallId)
        assertEquals("my-offering", params.offeringId)
        assertEquals(offering, params.offering)
    }

    @Test
    fun `constructor with offering defaults paywallId to null`() {
        val offering = FakeOffering(identifier = "my-offering")
        val params = CustomPaywallImpressionParams(offering = offering)

        assertNotNull(params)
        assertNull(params.paywallId)
        assertEquals("my-offering", params.offeringId)
        assertEquals(offering, params.offering)
    }

    private class FakeOffering(
        override val identifier: String,
    ) : Offering {
        override val serverDescription: String = "server description"
        override val metadata: Map<String, Any> = emptyMap()
        override val availablePackages: List<Package> = emptyList()
        override val lifetime: Package? = null
        override val annual: Package? = null
        override val sixMonth: Package? = null
        override val threeMonth: Package? = null
        override val twoMonth: Package? = null
        override val monthly: Package? = null
        override val weekly: Package? = null
        override val webCheckoutUrl: String? = null
    }
}
