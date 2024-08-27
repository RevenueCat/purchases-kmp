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
    override val availablePackages: List<Package> by lazy {
        wrapped.availablePackages.map { it.toPackage() }
    }
    override val lifetime: Package? by lazy { wrapped.lifetime?.toPackage() }
    override val annual: Package? by lazy { wrapped.annual?.toPackage() }
    override val sixMonth: Package? by lazy { wrapped.sixMonth?.toPackage() }
    override val threeMonth: Package? by lazy { wrapped.threeMonth?.toPackage() }
    override val twoMonth: Package? by lazy { wrapped.twoMonth?.toPackage() }
    override val monthly: Package? by lazy { wrapped.monthly?.toPackage() }
    override val weekly: Package? by lazy { wrapped.weekly?.toPackage() }
}
