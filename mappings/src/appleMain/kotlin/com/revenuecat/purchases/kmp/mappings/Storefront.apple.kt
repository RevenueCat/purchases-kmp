package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.Storefront
import com.revenuecat.purchases.kn.core.RCStorefront

public fun RCStorefront.toStorefront(): Storefront =
    Storefront(
        countryCode = countryCode(),
    )
