package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import kotlinx.cinterop.convert
import platform.Foundation.NSError
import platform.Foundation.NSLocalizedDescriptionKey
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ErrorsTest {

    @Test
    fun `maps known error codes to the corresponding PurchasesErrorCode`() {
        assertEquals(PurchasesErrorCode.UnknownError, nsError(code = 0).toPurchasesErrorOrThrow().code)
        assertEquals(PurchasesErrorCode.PurchaseCancelledError, nsError(code = 1).toPurchasesErrorOrThrow().code)
        assertEquals(
            PurchasesErrorCode.TestStoreSimulatedPurchaseError,
            nsError(code = 42).toPurchasesErrorOrThrow().code,
        )
    }

    @Test
    fun `maps the localized description to the underlying error message`() {
        val error = nsError(code = 10, description = "network down").toPurchasesErrorOrThrow()

        assertEquals(PurchasesErrorCode.NetworkError, error.code)
        assertEquals("network down", error.underlyingErrorMessage)
    }

    @Test
    fun `throws on unknown error codes`() {
        assertFailsWith<IllegalStateException> { nsError(code = 12345).toPurchasesErrorOrThrow() }
    }

    private fun nsError(code: Int, description: String? = null): NSError =
        NSError.errorWithDomain(
            domain = "RCPurchasesErrorDomain",
            code = code.convert(),
            userInfo = description?.let { mapOf(NSLocalizedDescriptionKey to it) },
        )
}
