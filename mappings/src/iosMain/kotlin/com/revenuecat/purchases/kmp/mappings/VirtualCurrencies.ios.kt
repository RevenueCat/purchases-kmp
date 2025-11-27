package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCVirtualCurrency
import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import com.revenuecat.purchases.kmp.models.VirtualCurrencies
import swiftPMImport.com.revenuecat.purchases.models.RCVirtualCurrencies as IosVirtualCurrencies

public fun IosVirtualCurrencies.toVirtualCurrencies(): VirtualCurrencies {
    return VirtualCurrencies(
        all = all().mapEntries {
            it.key as String to (it.value as RCVirtualCurrency).toVirtualCurrency()
        }
    )
}
