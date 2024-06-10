package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.Offering
import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.kmp.annual
import com.revenuecat.purchases.kmp.availablePackages
import com.revenuecat.purchases.kmp.get
import com.revenuecat.purchases.kmp.getMetadataString
import com.revenuecat.purchases.kmp.getPackage
import com.revenuecat.purchases.kmp.identifier
import com.revenuecat.purchases.kmp.lifetime
import com.revenuecat.purchases.kmp.metadata
import com.revenuecat.purchases.kmp.monthly
import com.revenuecat.purchases.kmp.serverDescription
import com.revenuecat.purchases.kmp.sixMonth
import com.revenuecat.purchases.kmp.threeMonth
import com.revenuecat.purchases.kmp.twoMonth
import com.revenuecat.purchases.kmp.weekly

@Suppress("unused", "UNUSED_VARIABLE")
private class OfferingAPI {
    fun check(offering: Offering) {
        with(offering) {
            val identifier: String = identifier
            val serverDescription: String = serverDescription
            val availablePackages: List<Package> = availablePackages
            val lifetime: Package? = lifetime
            val annual: Package? = annual
            val sixMonth: Package? = sixMonth
            val threeMonth: Package? = threeMonth
            val twoMonth: Package? = twoMonth
            val monthly: Package? = monthly
            val weekly: Package? = weekly
            val p1: Package? = offering[""]
            val p2: Package? = getPackage("")
            val metadata: Map<String, Any> = metadata
            val metadataString: String = getMetadataString("key", "default")
        }
    }
}
