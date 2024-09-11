package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.PackageType

@Suppress("unused")
private class PackageTypeAPI {

    fun check(type: PackageType) {
        when (type) {
            PackageType.UNKNOWN,
            PackageType.CUSTOM,
            PackageType.LIFETIME,
            PackageType.ANNUAL,
            PackageType.SIX_MONTH,
            PackageType.THREE_MONTH,
            PackageType.TWO_MONTH,
            PackageType.MONTHLY,
            PackageType.WEEKLY,
            -> {
            }
        }.exhaustive
    }
}
