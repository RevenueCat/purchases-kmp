package io.shortway.kobankat.apitester

import io.shortway.kobankat.Offering
import io.shortway.kobankat.Offerings
import io.shortway.kobankat.all
import io.shortway.kobankat.current
import io.shortway.kobankat.get
import io.shortway.kobankat.getCurrentOfferingForPlacement
import io.shortway.kobankat.getOffering

@Suppress("unused", "UNUSED_VARIABLE")
private class OfferingsAPI {
    fun check(offerings: Offerings) {
        with(offerings) {
            val current: Offering? = current
            val all: Map<String, Offering> = all
            val o1: Offering? = getOffering("")
            val o2: Offering? = this[""]
            val o3: Offering? = getCurrentOfferingForPlacement("")
        }
    }
}
