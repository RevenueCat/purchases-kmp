package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.Offering
import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import cocoapods.PurchasesHybridCommon.RCOffering as NativeIosOffering

public fun NativeIosOffering.toOffering(): Offering =
    IosOffering(this)

public fun Offering.toIosOffering(): NativeIosOffering =
    (this as IosOffering).iosOffering

private class IosOffering(val iosOffering: NativeIosOffering): Offering {
    override val identifier: String
        get() = iosOffering.identifier()
    override val serverDescription: String
        get() = iosOffering.serverDescription()
    override val metadata: Map<String, Any>
        get() = iosOffering.metadata().mapEntries { (key, value) -> key as String to value as Any }
    override val availablePackages: List<Package>
        get() = iosOffering.availablePackages().map { it as Package }
    override val lifetime: Package?
        get() = iosOffering.lifetime()?.toPackage()
    override val annual: Package?
        get() = iosOffering.annual()?.toPackage()
    override val sixMonth: Package?
        get() = iosOffering.sixMonth()?.toPackage()
    override val threeMonth: Package?
        get() = iosOffering.threeMonth()?.toPackage()
    override val twoMonth: Package?
        get() = iosOffering.twoMonth()?.toPackage()
    override val monthly: Package?
        get() = iosOffering.monthly()?.toPackage()
    override val weekly: Package?
        get() = iosOffering.weekly()?.toPackage()
}
