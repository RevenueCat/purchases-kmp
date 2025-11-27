package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.currentOfferingForPlacement
import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import com.revenuecat.purchases.kmp.models.Offerings
import swiftPMImport.com.revenuecat.purchases.models.RCOffering as IosOffering
import swiftPMImport.com.revenuecat.purchases.models.RCOfferings as IosOfferings

public fun IosOfferings.toOfferings(): Offerings =
    Offerings(
        current = current()?.toOffering(),
        all = all().mapEntries { (offeringId, offering) ->
            offeringId as String to (offering as IosOffering).toOffering()
        },
        getCurrentOfferingForPlacement = {
            currentOfferingForPlacement(it)?.toOffering()
        }
    )
