package io.shortway.kobankat

public data class PurchasesConfiguration(
    internal val apiKey: String,
    internal val appUserId: String,
    internal val observerMode: Boolean,
    internal val showInAppMessagesAutomatically: Boolean,
    internal val dangerousSettings: DangerousSettings,
    internal val verificationMode: EntitlementVerificationMode,
)