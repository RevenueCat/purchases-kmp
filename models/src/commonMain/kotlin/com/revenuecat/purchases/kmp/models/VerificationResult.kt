package com.revenuecat.purchases.kmp.models

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
public enum class VerificationResult {
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

    /**
     * Whether the result has been verified.
     */
    public val isVerified: Boolean
        // Ideally we would get this from the native layer, but we have had issues
        // accessing this in the iOS layer. For now, we're duplicating this logic.
        get() = when (this) {
            VERIFIED,
            VERIFIED_ON_DEVICE -> true

            FAILED,
            NOT_REQUESTED -> false
        }
}
