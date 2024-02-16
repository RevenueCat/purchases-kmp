package io.shortway.kobankat

import cocoapods.RevenueCat.RCOfferings
import io.shortway.kobankat.ktx.mapEntries

public actual typealias Offerings = RCOfferings

public actual val Offerings.current: Offering?
    get() = current()

public actual val Offerings.all: Map<String, Offering>
    get() = all().mapEntries { (offeringId, offering) ->
        offeringId as String to offering as Offering
    }