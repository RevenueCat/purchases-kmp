package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.VirtualCurrency
import com.revenuecat.purchases.virtualcurrencies.VirtualCurrency as AndroidVirtualCurrency

public fun AndroidVirtualCurrency.toVirtualCurrency(): VirtualCurrency {
    return VirtualCurrency(
        balance = balance,
        name = name,
        code = code,
        serverDescription = serverDescription
    )
}
