@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.Offering as RcOffering

public actual typealias Offering = RcOffering

public actual val Offering.identifier: String
    get() = identifier

public actual val Offering.serverDescription: String
    get() = serverDescription

public actual val Offering.metadata: Map<String, Any>
    get() = metadata

public actual val Offering.availablePackages: List<Package>
    get() = availablePackages

public actual val Offering.lifetime: Package?
    get() = lifetime

public actual val Offering.annual: Package?
    get() = annual

public actual val Offering.sixMonth: Package?
    get() = sixMonth

public actual val Offering.threeMonth: Package?
    get() = threeMonth

public actual val Offering.twoMonth: Package?
    get() = twoMonth

public actual val Offering.monthly: Package?
    get() = monthly

public actual val Offering.weekly: Package?
    get() = weekly
