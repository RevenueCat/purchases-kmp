@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat

import com.revenuecat.purchases.Offerings as RcOfferings

public actual typealias Offerings = RcOfferings

public actual val Offerings.current: Offering?
    get() = current

public actual val Offerings.all: Map<String, Offering>
    get() = all

public actual fun Offerings.getCurrentOfferingForPlacement(placementId: String): Offering? =
    getCurrentOfferingForPlacement(placementId)
