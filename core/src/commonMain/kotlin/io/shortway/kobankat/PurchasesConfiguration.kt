package io.shortway.kobankat

import kotlin.jvm.JvmSynthetic

/**
 * Configuration of the SDK.
 */
public class PurchasesConfiguration private constructor(
    public val apiKey: String,
    public val appUserId: String?,
    public val observerMode: Boolean,
    public val userDefaultsSuiteName: String?,
    public val showInAppMessagesAutomatically: Boolean,
    public val store: Store?,
    public val diagnosticsEnabled: Boolean,
    public val dangerousSettings: DangerousSettings,
    public val verificationMode: EntitlementVerificationMode,
) {
    override fun toString(): String =
        "PurchasesConfiguration(" +
                "apiKey=$apiKey, " +
                "appUserId=$appUserId, " +
                "observerMode=$observerMode, " +
                "userDefaultsSuiteName=$userDefaultsSuiteName, " +
                "showInAppMessagesAutomatically=$showInAppMessagesAutomatically, " +
                "store=$store, " +
                "diagnosticsEnabled=$diagnosticsEnabled, " +
                "dangerousSettings=$dangerousSettings, " +
                "verificationMode=$verificationMode" +
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
        public var userDefaultsSuiteName: String? = null

        @set:JvmSynthetic
        public var showInAppMessagesAutomatically: Boolean = true

        @set:JvmSynthetic
        public var store: Store? = null

        @set:JvmSynthetic
        public var diagnosticsEnabled: Boolean = false

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

        public fun userDefaultsSuiteName(userDefaultsSuiteName: String?): Builder =
            apply { this.userDefaultsSuiteName = userDefaultsSuiteName }

        public fun showInAppMessagesAutomatically(showInAppMessagesAutomatically: Boolean): Builder =
            apply { this.showInAppMessagesAutomatically = showInAppMessagesAutomatically }

        public fun store(store: Store?): Builder =
            apply { this.store = store }

        public fun diagnosticsEnabled(diagnosticsEnabled: Boolean): Builder =
            apply { this.diagnosticsEnabled = diagnosticsEnabled }

        public fun dangerousSettings(dangerousSettings: DangerousSettings): Builder =
            apply { this.dangerousSettings = dangerousSettings }

        public fun verificationMode(verificationMode: EntitlementVerificationMode): Builder =
            apply { this.verificationMode = verificationMode }

        public fun build(): PurchasesConfiguration = PurchasesConfiguration(
            apiKey = apiKey,
            appUserId = appUserId,
            observerMode = observerMode,
            userDefaultsSuiteName = userDefaultsSuiteName,
            showInAppMessagesAutomatically = showInAppMessagesAutomatically,
            store = store,
            diagnosticsEnabled = diagnosticsEnabled,
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
