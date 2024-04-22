package io.shortway.kobankat.apitester

import io.shortway.kobankat.Offering
import io.shortway.kobankat.Package
import io.shortway.kobankat.annual
import io.shortway.kobankat.availablePackages
import io.shortway.kobankat.get
import io.shortway.kobankat.getMetadataString
import io.shortway.kobankat.getPackage
import io.shortway.kobankat.identifier
import io.shortway.kobankat.lifetime
import io.shortway.kobankat.metadata
import io.shortway.kobankat.monthly
import io.shortway.kobankat.serverDescription
import io.shortway.kobankat.sixMonth
import io.shortway.kobankat.threeMonth
import io.shortway.kobankat.twoMonth
import io.shortway.kobankat.weekly

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
