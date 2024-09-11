package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.models.Offerings

@Suppress("unused", "UNUSED_VARIABLE")
private class OfferingsAPI {
    fun check(offerings: Offerings) {
        with(offerings) {
            val current: Offering? = current
            val all: Map<String, Offering> = all
            val o1: Offering? = getOffering("")
            val o2: Offering? = this[""]
        }
    }
}
