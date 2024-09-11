package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import com.revenuecat.purchases.kmp.models.PurchasesException

@Suppress("unused", "UNUSED_VARIABLE")
private class PurchasesExceptionAPI {
    fun check(exception: PurchasesException) {
        val underlyingErrorMessage: String? = exception.underlyingErrorMessage
        val message: String = exception.message
        val code: PurchasesErrorCode = exception.code
        val error: PurchasesError = exception.error
    }
}
