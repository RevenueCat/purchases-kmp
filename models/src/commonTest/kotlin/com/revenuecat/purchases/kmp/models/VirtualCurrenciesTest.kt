package com.revenuecat.purchases.kmp.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class VirtualCurrenciesTest {

    @Test
    fun `get returns virtual currency when code exists`() {
        val gold = VirtualCurrency(
            balance = 100,
            name = "Gold",
            code = "GLD",
            serverDescription = "It's gold"
        )

        val virtualCurrencies = VirtualCurrencies(
            all = mapOf(
                "GLD" to gold,
            )
        )
        
        assertEquals(gold, virtualCurrencies["GLD"])
    }

    @Test
    fun `get returns null when VC code does not exist`() {
        val virtualCurrencies = VirtualCurrencies(
            all = emptyMap()
        )
        
        assertNull(virtualCurrencies["GLD"])
    }
}
