package io.shortway.kobankat

/**
 * Verification strictness levels for [EntitlementInfo].
 */
public enum class EntitlementVerificationMode {
    /**
     * The SDK will not perform any entitlement verification.
     */
    DISABLED,

    /**
     * Enable entitlement verification.
     */
    INFORMATIONAL,
}