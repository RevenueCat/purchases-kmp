package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.VirtualCurrencies
import com.revenuecat.purchases.virtualcurrencies.VirtualCurrencies as AndroidVirtualCurrencies

public fun AndroidVirtualCurrencies.toVirtualCurrencies(): VirtualCurrencies {
    return VirtualCurrencies(
        all = this.all.mapValues { (_, virtualCurrency) -> virtualCurrency.toVirtualCurrency() }
    )
}
