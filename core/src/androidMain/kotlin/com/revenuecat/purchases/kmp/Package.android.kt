@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.Package as RcPackage
import com.revenuecat.purchases.PackageType as AndroidPackageType

public actual typealias Package = RcPackage

public actual val Package.identifier: String
    get() = identifier
public actual val Package.packageType: PackageType
    get() = packageType.toPackageType()
public actual val Package.storeProduct: StoreProduct
    get() = StoreProduct(product)
public actual val Package.presentedOfferingContext: PresentedOfferingContext
    get() = PresentedOfferingContext(presentedOfferingContext)

private fun AndroidPackageType.toPackageType(): PackageType =
    when (this) {
        AndroidPackageType.UNKNOWN -> PackageType.UNKNOWN
        AndroidPackageType.CUSTOM -> PackageType.CUSTOM
        AndroidPackageType.LIFETIME -> PackageType.LIFETIME
        AndroidPackageType.ANNUAL -> PackageType.ANNUAL
        AndroidPackageType.SIX_MONTH -> PackageType.SIX_MONTH
        AndroidPackageType.THREE_MONTH -> PackageType.THREE_MONTH
        AndroidPackageType.TWO_MONTH -> PackageType.TWO_MONTH
        AndroidPackageType.MONTHLY -> PackageType.MONTHLY
        AndroidPackageType.WEEKLY -> PackageType.WEEKLY
    }
