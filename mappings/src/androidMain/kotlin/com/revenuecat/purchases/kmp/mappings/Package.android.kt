package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PackageType
import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.Package as NativeAndroidPackage
import com.revenuecat.purchases.kmp.Package as RCPackage
import com.revenuecat.purchases.PackageType as AndroidPackageType

public fun NativeAndroidPackage.toPackage(): RCPackage = AndroidPackage(this)

public fun RCPackage.toAndroidPackage(): NativeAndroidPackage = (this as AndroidPackage).wrapped

private class AndroidPackage(
    val wrapped: NativeAndroidPackage
): RCPackage {
    override val identifier: String = wrapped.identifier
    override val packageType: PackageType = wrapped.packageType.toPackageType()
    override val storeProduct: StoreProduct = wrapped.product.toStoreProduct()
    override val presentedOfferingContext: PresentedOfferingContext = wrapped.presentedOfferingContext.toPresentedOfferingContext()
}

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
