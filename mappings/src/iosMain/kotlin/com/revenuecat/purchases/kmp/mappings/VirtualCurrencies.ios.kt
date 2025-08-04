package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCVirtualCurrency
import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import com.revenuecat.purchases.kmp.models.VirtualCurrencies
import cocoapods.PurchasesHybridCommon.RCVirtualCurrencies as IosVirtualCurrencies

public fun IosVirtualCurrencies.toVirtualCurrencies(): VirtualCurrencies {
    return VirtualCurrencies(
        all = all().mapEntries {
            it.key as String to (it.value as RCVirtualCurrency).toVirtualCurrency()
        }
    )
}
