package io.shortway.kobankat.apitester

import io.shortway.kobankat.PurchasesError
import io.shortway.kobankat.PurchasesErrorCode

@Suppress("unused", "UNUSED_VARIABLE")
private class PurchasesErrorAPI {
    fun check(error: PurchasesError) {
        val code: PurchasesErrorCode = error.code
        val message: String = error.message
        val underlyingErrorMessage: String? = error.underlyingErrorMessage
    }
}
