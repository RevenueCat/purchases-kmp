package io.shortway.kobankat

/**
 * Configuration of the SDK.
 */
public data class PurchasesConfiguration(
    internal val apiKey: String,
    internal val appUserId: String? = null,
    internal val observerMode: Boolean = false,
    internal val showInAppMessagesAutomatically: Boolean = true,
    internal val dangerousSettings: DangerousSettings = DangerousSettings(),
    internal val verificationMode: EntitlementVerificationMode = EntitlementVerificationMode.DISABLED,
)