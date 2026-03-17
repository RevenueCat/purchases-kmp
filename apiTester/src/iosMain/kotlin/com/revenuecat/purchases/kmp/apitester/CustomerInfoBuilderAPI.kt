package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.mappings.buildCustomerInfo
import com.revenuecat.purchases.kmp.models.CustomerInfo

@Suppress("unused", "UNUSED_VARIABLE")
private class CustomerInfoBuilderAPI {
    fun check(customerInfoMap: Map<Any?, *>) {
        val result: Result<CustomerInfo> = buildCustomerInfo(customerInfoMap)
    }
}
