package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.PurchasesErrorCode

@Suppress("unused", "UNUSED_VARIABLE")
private class PurchasesErrorAPI {
    fun check(error: PurchasesError) {
        val code: PurchasesErrorCode = error.code
        val message: String = error.message
        val underlyingErrorMessage: String? = error.underlyingErrorMessage
    }
}
