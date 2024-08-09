package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.OwnershipType

@Suppress("unused")
private class OwnershipTypeAPI {
    fun check(type: OwnershipType) {
        when (type) {
            OwnershipType.PURCHASED,
            OwnershipType.FAMILY_SHARED,
            OwnershipType.UNKNOWN,
            -> {
            }
        }.exhaustive
    }
}
