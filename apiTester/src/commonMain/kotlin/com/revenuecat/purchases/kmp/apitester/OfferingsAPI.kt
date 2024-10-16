package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.models.Offerings

@Suppress("unused", "UNUSED_VARIABLE")
private class OfferingsAPI {

    fun checkConstructor(
        all: Map<String, Offering>,
        current: Offering?,
        getCurrentOfferingForPlacement: (placementId: String) -> Offering?
    ) {
        val offerings1 = Offerings(
            all = all,
            current = current
        )

        val offerings2 = Offerings(
            all = all,
            current = current,
            getCurrentOfferingForPlacement = getCurrentOfferingForPlacement,
        )
    }

    fun check(offerings: Offerings) {
        with(offerings) {
            val current: Offering? = current
            val all: Map<String, Offering> = all
            val o1: Offering? = getOffering("")
            val o2: Offering? = this[""]
        }
    }
}
