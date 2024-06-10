package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.PurchasesErrorCode
import com.revenuecat.purchases.kmp.PurchasesTransactionException

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
