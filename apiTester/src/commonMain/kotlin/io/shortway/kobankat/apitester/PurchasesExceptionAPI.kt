package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.PurchasesErrorCode
import com.revenuecat.purchases.kmp.PurchasesException

@Suppress("unused", "UNUSED_VARIABLE")
private class PurchasesExceptionAPI {
    fun check(exception: PurchasesException) {
        val underlyingErrorMessage: String? = exception.underlyingErrorMessage
        val message: String = exception.message
        val code: PurchasesErrorCode = exception.code
        val error: PurchasesError = exception.error
    }
}
