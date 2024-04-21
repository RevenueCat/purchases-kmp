package io.shortway.kobankat.apitester

import io.shortway.kobankat.Package
import io.shortway.kobankat.PackageType
import io.shortway.kobankat.PresentedOfferingContext
import io.shortway.kobankat.identifier
import io.shortway.kobankat.models.StoreProduct
import io.shortway.kobankat.offeringIdentifier
import io.shortway.kobankat.packageType
import io.shortway.kobankat.presentedOfferingContext
import io.shortway.kobankat.product

@Suppress("unused", "UNUSED_VARIABLE")
private class PackageAPI {
    fun check(p: Package) {
        with(p) {
            val identifier: String = identifier
            val packageType: PackageType = packageType
            val product: StoreProduct = product
            val offering: String = offeringIdentifier
            val presentedOfferingContext: PresentedOfferingContext = presentedOfferingContext
        }
    }
}
