package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.Offering
import com.revenuecat.purchases.kmp.Offerings
import com.revenuecat.purchases.kmp.all
import com.revenuecat.purchases.kmp.current
import com.revenuecat.purchases.kmp.get
import com.revenuecat.purchases.kmp.getCurrentOfferingForPlacement
import com.revenuecat.purchases.kmp.getOffering

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
