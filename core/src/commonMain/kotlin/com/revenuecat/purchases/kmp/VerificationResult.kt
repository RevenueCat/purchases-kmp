package com.revenuecat.purchases.kmp

/**
 * The result of the verification process.
 *
 * This is accomplished by preventing MiTM attacks between the SDK and the RevenueCat server.
 * With verification enabled, the SDK ensures that the response created by the server was not
 * modified by a third-party, and the response received is exactly what was sent.
 *
 * - Note: Verification is only performed if enabled using PurchasesConfiguration.Builder's
 * entitlementVerificationMode property. This is disabled by default.
 */
public expect enum class VerificationResult {
    /**
     * No verification was done.
     *
     * This value is returned when verification is not enabled in PurchasesConfiguration
     */
    NOT_REQUESTED,

    /**
     * Verification with our server was performed successfully.
     */
    VERIFIED,

    /**
     * Verification failed, possibly due to a MiTM attack.
     */
    FAILED,

    /**
     * Verification was performed on device.
     */
    VERIFIED_ON_DEVICE,

    ;
}

/**
 * Whether the result is [VerificationResult.VERIFIED] or [VerificationResult.VERIFIED_ON_DEVICE].
 */
public val VerificationResult.isVerified: Boolean
    get() {
        return when (this) {
            VerificationResult.VERIFIED, VerificationResult.VERIFIED_ON_DEVICE -> true
            VerificationResult.NOT_REQUESTED, VerificationResult.FAILED -> false
            else -> error("Unexpected VerificationResult: $this")
        }
    }
