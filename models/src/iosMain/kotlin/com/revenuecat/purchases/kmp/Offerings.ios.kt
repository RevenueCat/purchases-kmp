package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCOfferings
import com.revenuecat.purchases.kmp.ktx.mapEntries

public actual typealias Offerings = RCOfferings

public actual val Offerings.current: Offering?
    get() = current()

public actual val Offerings.all: Map<String, Offering>
    get() = all().mapEntries { (offeringId, offering) ->
        offeringId as String to offering as Offering
    }
