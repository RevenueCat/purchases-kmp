package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PackageType
import com.revenuecat.purchases.kmp.models.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.Package as RCPackage
import com.revenuecat.purchases.kn.core.RCPackage as NativeIosPackage
import com.revenuecat.purchases.kn.core.RCPackageType as IosPackageType
import com.revenuecat.purchases.kn.core.RCPackageTypeAnnual as IosPackageTypeAnnual
import com.revenuecat.purchases.kn.core.RCPackageTypeCustom as IosPackageTypeCustom
import com.revenuecat.purchases.kn.core.RCPackageTypeLifetime as IosPackageTypeLifetime
import com.revenuecat.purchases.kn.core.RCPackageTypeMonthly as IosPackageTypeMonthly
import com.revenuecat.purchases.kn.core.RCPackageTypeSixMonth as IosPackageTypeSixMonth
import com.revenuecat.purchases.kn.core.RCPackageTypeThreeMonth as IosPackageTypeThreeMonth
import com.revenuecat.purchases.kn.core.RCPackageTypeTwoMonth as IosPackageTypeTwoMonth
import com.revenuecat.purchases.kn.core.RCPackageTypeUnknown as IosPackageTypeUnknown
import com.revenuecat.purchases.kn.core.RCPackageTypeWeekly as IosPackageTypeWeekly

public fun NativeIosPackage.toPackage(): RCPackage = IosPackage(this)

public fun RCPackage.toIosPackage(): NativeIosPackage = (this as IosPackage).iosPackage

private class IosPackage(val iosPackage: NativeIosPackage): RCPackage {
    override val identifier: String = iosPackage.identifier()
    override val packageType: PackageType = iosPackage.packageType().toPackageType()
    override val storeProduct: StoreProduct = iosPackage.storeProduct().toStoreProduct()
    override val presentedOfferingContext: PresentedOfferingContext = iosPackage.presentedOfferingContext().toPresentedOfferingContext()
    override val webCheckoutUrl: String? = iosPackage.webCheckoutUrl()?.absoluteString
}

private fun IosPackageType.toPackageType(): PackageType =
    when (this) {
        IosPackageTypeUnknown -> PackageType.UNKNOWN
        IosPackageTypeCustom -> PackageType.CUSTOM
        IosPackageTypeLifetime -> PackageType.LIFETIME
        IosPackageTypeAnnual -> PackageType.ANNUAL
        IosPackageTypeSixMonth -> PackageType.SIX_MONTH
        IosPackageTypeThreeMonth -> PackageType.THREE_MONTH
        IosPackageTypeTwoMonth -> PackageType.TWO_MONTH
        IosPackageTypeMonthly -> PackageType.MONTHLY
        IosPackageTypeWeekly -> PackageType.WEEKLY
        else -> error("Unexpected IosPackageType: $this")
    }
