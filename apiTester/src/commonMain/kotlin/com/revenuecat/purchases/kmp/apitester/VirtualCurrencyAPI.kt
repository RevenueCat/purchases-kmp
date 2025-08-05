package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.VirtualCurrency

@Suppress("unused", "UNUSED_VARIABLE")
private class VirtualCurrencyAPI {
    fun check(virtualCurrency: VirtualCurrency) {
        with(virtualCurrency) {
            val balance: Int = balance
            val name: String = name
            val code: String = code
            val serverDescription: String? = serverDescription
        }
    }
}
