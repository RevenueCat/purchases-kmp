package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.Offerings
import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import cocoapods.PurchasesHybridCommon.RCOffering as IosOffering
import cocoapods.PurchasesHybridCommon.RCOfferings as IosOfferings

public fun IosOfferings.toOfferings(): Offerings =
    Offerings(
        current = current()?.toOffering(),
        all = all().mapEntries { (offeringId, offering) ->
            offeringId as String to (offering as IosOffering).toOffering()
        }
    )
