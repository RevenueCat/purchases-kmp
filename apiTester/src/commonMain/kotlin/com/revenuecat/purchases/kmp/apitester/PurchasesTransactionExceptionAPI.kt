package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import com.revenuecat.purchases.kmp.models.PurchasesTransactionException

@Suppress("unused", "UNUSED_VARIABLE")
private class PurchasesTransactionExceptionAPI {
    fun check(exception: PurchasesTransactionException) {
        val underlyingErrorMessage: String? = exception.underlyingErrorMessage
        val message: String = exception.message
        val code: PurchasesErrorCode = exception.code
        val error: PurchasesError = exception.error
        val userCancelled: Boolean = exception.userCancelled
    }
}
