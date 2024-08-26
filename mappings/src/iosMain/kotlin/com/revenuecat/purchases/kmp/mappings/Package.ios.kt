package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PackageType
import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.StoreProduct
import cocoapods.PurchasesHybridCommon.RCPackage as NativeIosPackage
import cocoapods.PurchasesHybridCommon.RCPackageType as IosPackageType
import cocoapods.PurchasesHybridCommon.RCPackageTypeAnnual as IosPackageTypeAnnual
import cocoapods.PurchasesHybridCommon.RCPackageTypeCustom as IosPackageTypeCustom
import cocoapods.PurchasesHybridCommon.RCPackageTypeLifetime as IosPackageTypeLifetime
import cocoapods.PurchasesHybridCommon.RCPackageTypeMonthly as IosPackageTypeMonthly
import cocoapods.PurchasesHybridCommon.RCPackageTypeSixMonth as IosPackageTypeSixMonth
import cocoapods.PurchasesHybridCommon.RCPackageTypeThreeMonth as IosPackageTypeThreeMonth
import cocoapods.PurchasesHybridCommon.RCPackageTypeTwoMonth as IosPackageTypeTwoMonth
import cocoapods.PurchasesHybridCommon.RCPackageTypeUnknown as IosPackageTypeUnknown
import cocoapods.PurchasesHybridCommon.RCPackageTypeWeekly as IosPackageTypeWeekly
import com.revenuecat.purchases.kmp.Package as RCPackage

public fun NativeIosPackage.toPackage(): RCPackage = IosPackage(this)

public fun RCPackage.toIosPackage(): NativeIosPackage = (this as IosPackage).iosPackage

private class IosPackage(val iosPackage: NativeIosPackage): RCPackage {
    override val identifier: String = iosPackage.identifier()
    override val packageType: PackageType = iosPackage.packageType().toPackageType()
    override val storeProduct: StoreProduct = iosPackage.storeProduct().toStoreProduct()
    override val presentedOfferingContext: PresentedOfferingContext = iosPackage.presentedOfferingContext().toPresentedOfferingContext()
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
