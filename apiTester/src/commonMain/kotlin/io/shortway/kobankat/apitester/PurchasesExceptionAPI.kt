package io.shortway.kobankat.apitester

import io.shortway.kobankat.PurchasesError
import io.shortway.kobankat.PurchasesErrorCode
import io.shortway.kobankat.PurchasesException

@Suppress("unused", "UNUSED_VARIABLE")
private class PurchasesExceptionAPI {
    fun check(exception: PurchasesException) {
        val underlyingErrorMessage: String? = exception.underlyingErrorMessage
        val message: String = exception.message
        val code: PurchasesErrorCode = exception.code
        val error: PurchasesError = exception.error
    }
}
