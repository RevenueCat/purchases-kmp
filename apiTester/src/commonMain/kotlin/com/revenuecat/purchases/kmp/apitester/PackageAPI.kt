package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.StoreProduct

@Suppress("unused", "UNUSED_VARIABLE")
private class PackageAPI {
    fun check(p: Package) {
        with(p) {
            val identifier: String = identifier
            // FIXME re-enable in SDK-3529
            // val packageType: PackageType = packageType
            val product: StoreProduct = storeProduct
            // FIXME re-enable in SDK-3529
            // val presentedOfferingContext: PresentedOfferingContext = presentedOfferingContext
        }
    }
}
