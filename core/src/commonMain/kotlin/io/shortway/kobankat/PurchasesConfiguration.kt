package io.shortway.kobankat

import kotlin.jvm.JvmSynthetic

/**
 * Configuration of the SDK.
 */
public class PurchasesConfiguration private constructor(
    public val apiKey: String,
    public val appUserId: String? = null,
    public val observerMode: Boolean = false,
    public val showInAppMessagesAutomatically: Boolean = true,
    public val dangerousSettings: DangerousSettings = DangerousSettings(),
    public val verificationMode: EntitlementVerificationMode = EntitlementVerificationMode.DISABLED,
) {
    override fun toString(): String =
        "PurchasesConfiguration(" +
                "apiKey=$apiKey, " +
                "appUserId=$appUserId, " +
                "observerMode=$observerMode, " +
                "showInAppMessagesAutomatically=$apiKey, " +
                "dangerousSettings=$appUserId, " +
                "verificationMode=$observerMode" +
                ")"

    public class Builder(
        @set:JvmSynthetic
        public var apiKey: String
    ) {
        @set:JvmSynthetic
        public var appUserId: String? = null

        @set:JvmSynthetic
        public var observerMode: Boolean = false

        @set:JvmSynthetic
        public var showInAppMessagesAutomatically: Boolean = true

        @set:JvmSynthetic
        public var dangerousSettings: DangerousSettings = DangerousSettings()

        @set:JvmSynthetic
        public var verificationMode: EntitlementVerificationMode =
            EntitlementVerificationMode.DISABLED

        public fun apiKey(apiKey: String): Builder =
            apply { this.apiKey = apiKey }

        public fun appUserId(appUserId: String): Builder =
            apply { this.appUserId = appUserId }

        public fun observerMode(observerMode: Boolean): Builder =
            apply { this.observerMode = observerMode }

        public fun showInAppMessagesAutomatically(showInAppMessagesAutomatically: Boolean): Builder =
            apply { this.showInAppMessagesAutomatically = showInAppMessagesAutomatically }

        public fun dangerousSettings(dangerousSettings: DangerousSettings): Builder =
            apply { this.dangerousSettings = dangerousSettings }

        public fun verificationMode(verificationMode: EntitlementVerificationMode): Builder =
            apply { this.verificationMode = verificationMode }

        public fun build(): PurchasesConfiguration = PurchasesConfiguration(
            apiKey = apiKey,
            appUserId = appUserId,
            observerMode = observerMode,
            showInAppMessagesAutomatically = showInAppMessagesAutomatically,
            dangerousSettings = dangerousSettings,
            verificationMode = verificationMode
        )
    }
}

@JvmSynthetic
public fun PurchasesConfiguration(
    apiKey: String,
    builder: PurchasesConfiguration.Builder.() -> Unit = { },
): PurchasesConfiguration =
    PurchasesConfiguration
        .Builder(apiKey)
        .apply(builder)
        .build()

@JvmSynthetic
public fun PurchasesFactory.configure(
    apiKey: String,
    builder: PurchasesConfiguration.Builder.() -> Unit = { }
): Purchases =
    configure(PurchasesConfiguration(apiKey, builder))
