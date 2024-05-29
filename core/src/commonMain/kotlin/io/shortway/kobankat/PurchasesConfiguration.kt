package io.shortway.kobankat

import io.shortway.kobankat.PurchasesConfiguration.Builder
import io.shortway.kobankat.PurchasesFactory.sharedInstance
import kotlin.jvm.JvmSynthetic

/**
 * Holds parameters to initialize the SDK. Create an instance of this class using the [Builder] and
 * pass it to [PurchasesFactory.configure].
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

    /**
     * Use this builder to create an instance of [PurchasesConfiguration].
     */
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

        /**
         * Your RevenueCat API Key.
         */
        public fun apiKey(apiKey: String): Builder =
            apply { this.apiKey = apiKey }

        /**
         * A unique id for identifying the user
         */
        public fun appUserId(appUserId: String): Builder =
            apply { this.appUserId = appUserId }

        /**
         * An optional boolean. Set this to TRUE if you have your own IAP implementation and
         * want to use only RevenueCat's backend. Default is FALSE. If you are on Android and setting this to TRUE,
         * you will have to acknowledge the purchases yourself.
         */
        public fun observerMode(observerMode: Boolean): Builder =
            apply { this.observerMode = observerMode }

        /**
         * iOS-only, will be ignored for Android. Set this if you would like the RevenueCat SDK to
         * store its preferences in a different NSUserDefaults suite, otherwise it will use
         * standardUserDefaults. Default is null, which will make the SDK use standardUserDefaults.
         */
        public fun userDefaultsSuiteName(userDefaultsSuiteName: String?): Builder =
            apply { this.userDefaultsSuiteName = userDefaultsSuiteName }

        /**
         * Enable this setting to show in-app messages from Google Play automatically. Default is
         * enabled.
         * For more info: [rev.cat](https://rev.cat/googleplayinappmessaging)
         *
         * If this setting is disabled, you can show the snackbar by calling
         * [Purchases.showInAppMessagesIfNeeded]
         */
        public fun showInAppMessagesAutomatically(showInAppMessagesAutomatically: Boolean): Builder =
            apply { this.showInAppMessagesAutomatically = showInAppMessagesAutomatically }

        /**
         * The store in which to make purchases. See [Store] for supported stores.
         */
        public fun store(store: Store?): Builder =
            apply { this.store = store }

        /**
         * Enabling diagnostics will send some performance and debugging information from the SDK to our servers.
         * Examples of this information include response times, cache hits or error codes.
         * This information will be anonymized so it can't be traced back to the end-user.
         * The default value is false.
         *
         * Diagnostics is only available in Android API 24+
         */
        public fun diagnosticsEnabled(diagnosticsEnabled: Boolean): Builder =
            apply { this.diagnosticsEnabled = diagnosticsEnabled }

        /**
         * Only use a Dangerous Setting if suggested by RevenueCat support team.
         */
        public fun dangerousSettings(dangerousSettings: DangerousSettings): Builder =
            apply { this.dangerousSettings = dangerousSettings }

        /**
         * Sets the [EntitlementVerificationMode] to perform signature verification of requests to the
         * RevenueCat backend.
         *
         * When changing from [EntitlementVerificationMode.DISABLED] to other modes, the SDK will clear the
         * CustomerInfo cache.
         * This means users will need to connect to the internet to get their entitlements back.
         *
         * The result of the verification can be obtained from [EntitlementInfos.verification] or
         * [EntitlementInfo.verification].
         *
         * Default mode is disabled. Please see https://rev.cat/trusted-entitlements for more info.
         */
        public fun verificationMode(verificationMode: EntitlementVerificationMode): Builder =
            apply { this.verificationMode = verificationMode }

        /**
         * Creates a [PurchasesConfiguration] instance with the specified properties.
         */
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

/**
 * Holds parameters to initialize the SDK. Create an instance of this class using the [Builder] and
 * pass it to [PurchasesFactory.configure].
 */
@JvmSynthetic
public fun PurchasesConfiguration(
    apiKey: String,
    builder: Builder.() -> Unit = { },
): PurchasesConfiguration =
    Builder(apiKey)
        .apply(builder)
        .build()

/**
 * Configures an instance of the SDK with the specified [configuration builder][builder]. The
 * instance will be set as a singleton. You should access the singleton instance using
 * [sharedInstance].
 */
@JvmSynthetic
public fun PurchasesFactory.configure(
    apiKey: String,
    builder: Builder.() -> Unit = { }
): Purchases =
    configure(PurchasesConfiguration(apiKey, builder))
