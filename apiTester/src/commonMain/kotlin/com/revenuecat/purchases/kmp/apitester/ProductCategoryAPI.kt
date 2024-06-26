package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.ProductCategory

@Suppress("unused")
private class ProductCategoryAPI {

    fun check(category: ProductCategory) {
        when (category) {
            ProductCategory.SUBSCRIPTION,
            ProductCategory.NON_SUBSCRIPTION,
            -> {
            }
        }.exhaustive
    }

}
