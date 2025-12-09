package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.VirtualCurrency
import swiftPMImport.com.revenuecat.purchases.models.RCVirtualCurrency as IosVirtualCurrency

public fun IosVirtualCurrency.toVirtualCurrency(): VirtualCurrency {
    return VirtualCurrency(
        balance = balance().toInt(),
        name = name(),
        code = code(),
        serverDescription = serverDescription()
    )
}
