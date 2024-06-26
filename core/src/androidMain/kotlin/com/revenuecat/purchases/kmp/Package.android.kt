@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.Package as RcPackage
import com.revenuecat.purchases.PackageType as RcPackageType

public actual typealias Package = RcPackage

public actual val Package.identifier: String
    get() = identifier
public actual val Package.packageType: PackageType
    get() = packageType.toPackageType()
public actual val Package.storeProduct: StoreProduct
    get() = StoreProduct(product)
public actual val Package.presentedOfferingContext: PresentedOfferingContext
    get() = presentedOfferingContext

internal fun RcPackageType.toPackageType(): PackageType =
    when (this) {
        RcPackageType.UNKNOWN -> PackageType.UNKNOWN
        RcPackageType.CUSTOM -> PackageType.CUSTOM
        RcPackageType.LIFETIME -> PackageType.LIFETIME
        RcPackageType.ANNUAL -> PackageType.ANNUAL
        RcPackageType.SIX_MONTH -> PackageType.SIX_MONTH
        RcPackageType.THREE_MONTH -> PackageType.THREE_MONTH
        RcPackageType.TWO_MONTH -> PackageType.TWO_MONTH
        RcPackageType.MONTHLY -> PackageType.MONTHLY
        RcPackageType.WEEKLY -> PackageType.WEEKLY
    }

public actual typealias PackageType = RcPackageType
