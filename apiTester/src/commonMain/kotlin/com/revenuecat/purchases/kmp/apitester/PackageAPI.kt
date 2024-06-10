package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.kmp.PackageType
import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.identifier
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.packageType
import com.revenuecat.purchases.kmp.presentedOfferingContext
import com.revenuecat.purchases.kmp.storeProduct

@Suppress("unused", "UNUSED_VARIABLE")
private class PackageAPI {
    fun check(p: Package) {
        with(p) {
            val identifier: String = identifier
            val packageType: PackageType = packageType
            val product: StoreProduct = storeProduct
            val presentedOfferingContext: PresentedOfferingContext = presentedOfferingContext
        }
    }
}
