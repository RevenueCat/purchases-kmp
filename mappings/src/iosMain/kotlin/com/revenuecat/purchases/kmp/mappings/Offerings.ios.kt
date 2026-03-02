package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import com.revenuecat.purchases.kmp.models.Offerings
import swiftPMImport.com.revenuecat.purchases.kn.core.currentOfferingForPlacement
import swiftPMImport.com.revenuecat.purchases.kn.core.RCOffering as IosOffering
import swiftPMImport.com.revenuecat.purchases.kn.core.RCOfferings as IosOfferings

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
