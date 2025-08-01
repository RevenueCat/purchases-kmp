package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.VirtualCurrencies
import com.revenuecat.purchases.kmp.models.VirtualCurrency

@Suppress("unused", "UNUSED_VARIABLE")
private class VirtualCurrenciesAPI {
    fun check(virtualCurrencies: VirtualCurrencies) {
        with(virtualCurrencies) {
            val all: Map<String, VirtualCurrency> = all
        }

        val vcFromSubscript: VirtualCurrency? = virtualCurrencies["GLD"]
        val vcFromGet: VirtualCurrency? = virtualCurrencies.get("GLD")
    }
}
