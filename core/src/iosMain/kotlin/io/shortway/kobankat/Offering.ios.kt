package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCOffering
import io.shortway.kobankat.ktx.mapEntries

public actual typealias Offering = RCOffering

public actual val Offering.identifier: String
    get() = identifier()

public actual val Offering.serverDescription: String
    get() = serverDescription()

public actual val Offering.metadata: Map<String, Any>
    get() = metadata().mapEntries { (key, value) -> key as String to value as Any}

public actual val Offering.availablePackages: List<Package>
    get() = availablePackages().map { it as Package }

public actual val Offering.lifetime: Package?
    get() = lifetime()

public actual val Offering.annual: Package?
    get() = annual()

public actual val Offering.sixMonth: Package?
    get() = sixMonth()

public actual val Offering.threeMonth: Package?
    get() = threeMonth()

public actual val Offering.twoMonth: Package?
    get() = twoMonth()

public actual val Offering.monthly: Package?
    get() = monthly()

public actual val Offering.weekly: Package?
    get() = weekly()
