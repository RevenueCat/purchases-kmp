package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCOfferings
import io.shortway.kobankat.ktx.mapEntries

public actual typealias Offerings = RCOfferings

public actual val Offerings.current: Offering?
    get() = current()

public actual val Offerings.all: Map<String, Offering>
    get() = all().mapEntries { (offeringId, offering) ->
        offeringId as String to offering as Offering
    }

public actual fun Offerings.getCurrentOfferingForPlacement(placementId: String): Offering? =
    TODO() // FIXME no public api available through ObjC in PHC?
