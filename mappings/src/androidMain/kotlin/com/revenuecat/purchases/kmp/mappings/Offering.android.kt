package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.Offering
import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.Offering as NativeAndroidOffering

public fun NativeAndroidOffering.toOffering(): Offering =
    AndroidOffering(this)

public fun Offering.toAndroidOffering(): NativeAndroidOffering =
    (this as AndroidOffering).wrapped

private class AndroidOffering(val wrapped: NativeAndroidOffering): Offering {
    override val identifier: String
        get() = wrapped.identifier
    override val serverDescription: String
        get() = wrapped.serverDescription
    override val metadata: Map<String, Any>
        get() = wrapped.metadata
    override val availablePackages: List<Package>
        get() = wrapped.availablePackages.map { it.toPackage() }
    override val lifetime: Package?
        get() = wrapped.lifetime?.toPackage()
    override val annual: Package?
        get() = wrapped.annual?.toPackage()
    override val sixMonth: Package?
        get() = wrapped.sixMonth?.toPackage()
    override val threeMonth: Package?
        get() = wrapped.threeMonth?.toPackage()
    override val twoMonth: Package?
        get() = wrapped.twoMonth?.toPackage()
    override val monthly: Package?
        get() = wrapped.monthly?.toPackage()
    override val weekly: Package?
        get() = wrapped.weekly?.toPackage()
}
