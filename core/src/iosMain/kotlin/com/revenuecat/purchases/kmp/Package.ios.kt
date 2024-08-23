package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCPackage
import com.revenuecat.purchases.kmp.models.StoreProduct
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

public actual typealias Package = RCPackage

public actual val Package.identifier: String
    get() = identifier()
public actual val Package.packageType: PackageType
    get() = packageType().toPackageType()
public actual val Package.storeProduct: StoreProduct
    get() = StoreProduct(storeProduct())
public actual val Package.presentedOfferingContext: PresentedOfferingContext
    get() = presentedOfferingContext().toPresentedOfferingContext()

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
