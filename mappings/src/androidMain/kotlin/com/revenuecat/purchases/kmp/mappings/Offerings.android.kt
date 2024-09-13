package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.Offerings as AndroidOfferings

public fun AndroidOfferings.toOfferings(): Offerings {
    return Offerings(
        all = all.mapValues { it.value.toOffering() },
        current = current?.toOffering()
    )
}
