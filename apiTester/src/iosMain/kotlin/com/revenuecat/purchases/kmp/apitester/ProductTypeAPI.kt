package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ProductType

@Suppress("unused")
private class ProductTypeAPI {
    fun check(type: ProductType) {
        when (type) {
            ProductType.SUBS,
            ProductType.INAPP,
            ProductType.UNKNOWN,
            -> {
            }
        }.exhaustive
    }
}
