package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCStorefront
import com.revenuecat.purchases.kmp.models.Storefront

public fun RCStorefront.toStorefront(): Storefront =
    Storefront(
        countryCode = countryCode(),
    )
