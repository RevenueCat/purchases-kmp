package com.revenuecat.purchases.kmp.models

/**
 * This class represents an error
 * @param code Error code
 * @param underlyingErrorMessage Optional error message with a more detailed explanation of the
 * error that originated this.
 */
public class PurchasesError(
    public val code: PurchasesErrorCode,
    public val underlyingErrorMessage: String? = null,
) {

    public val message: String = code.description

    override fun toString(): String {
        return "PurchasesError(code=$code, underlyingErrorMessage=$underlyingErrorMessage, message='$message')"
    }
}
