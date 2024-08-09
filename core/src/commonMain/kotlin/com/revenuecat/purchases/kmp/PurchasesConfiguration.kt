package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.PurchasesConfiguration.Builder
import kotlin.jvm.JvmSynthetic

/**
 * Holds parameters to initialize the SDK. Create an instance of this class using the [Builder] and
 * pass it to [Purchases.configure].
 */
public class PurchasesConfiguration private constructor(
    public val apiKey: String,
    public val appUserId: String?,
    public val purchasesAreCompletedBy: PurchasesAreCompletedBy,
    public val userDefaultsSuiteName: String?,
    public val storeKitVersion: StoreKitVersion,
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
                "purchasesAreCompletedBy=$purchasesAreCompletedBy, " +
                "userDefaultsSuiteName=$userDefaultsSuiteName, " +
                "showInAppMessagesAutomatically=$showInAppMessagesAutomatically, " +
                "store=$store, " +
                "diagnosticsEnabled=$diagnosticsEnabled, " +
                "dangerousSettings=$dangerousSettings, " +
                "verificationMode=$verificationMode" +
                ")"

    internal fun storeKitVersionToUse(): StoreKitVersion {
        var storeKitVersionToUse = this.storeKitVersion

        if (this.purchasesAreCompletedBy is PurchasesAreCompletedBy.MyApp) {
            storeKitVersionToUse = (this.purchasesAreCompletedBy as PurchasesAreCompletedBy.MyApp).storeKitVersion

            if (storeKitVersionToUse != this.storeKitVersion) {
                println(
                    "Warning: The storeKitVersion in purchasesAreCompletedBy does not match the " +
                            "provided storeKitVersion parameter. We will use the value found in purchasesAreCompletedBy."
                )
            }
        }

        return storeKitVersionToUse
    }

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
        public var purchasesAreCompletedBy: PurchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat

        @set:JvmSynthetic
        public var userDefaultsSuiteName: String? = null

        @set:JvmSynthetic
        public var storeKitVersion: StoreKitVersion = StoreKitVersion.DEFAULT

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
         * An optional setting. Set this to [MY_APP][PurchasesAreCompletedBy.MY_APP] if you have
         * your own IAP
         * implementation and want to use only RevenueCat's backend. Default is
         * [REVENUECAT][PurchasesAreCompletedBy.REVENUECAT]. If you are on Android and setting this
         * to [MY_APP][PurchasesAreCompletedBy.MY_APP], you will have to acknowledge the purchases
         * yourself.
         *
         * **Note:** failing to acknowledge a purchase within 3 days will lead to Google Play
         * automatically issuing a refund to the user.
         *
         * For more info, see
         * [revenuecat.com](https://www.revenuecat.com/docs/migrating-to-revenuecat/sdk-or-not/finishing-transactions)
         * and [developer.android.com](https://developer.android.com/google/play/billing/integrate#process).
         */
        public fun purchasesAreCompletedBy(
            purchasesAreCompletedBy: PurchasesAreCompletedBy
        ): Builder = apply { this.purchasesAreCompletedBy = purchasesAreCompletedBy }

        /**
         * iOS-only, will be ignored for Android. Set this if you would like the RevenueCat SDK to
         * store its preferences in a different NSUserDefaults suite, otherwise it will use
         * standardUserDefaults. Default is null, which will make the SDK use standardUserDefaults.
         */
        public fun userDefaultsSuiteName(userDefaultsSuiteName: String?): Builder =
            apply { this.userDefaultsSuiteName = userDefaultsSuiteName }

        /**
         * iOS-only, will be ignored for Android. By providing StoreKitVersion.defaultVersion,
         * RevenueCat will automatically select the most appropriate StoreKit version
         * for the app's runtime environment.
         *
         * **Warning:** Make sure you have an In-App Purchase Key configured in your app.
         * Please see []revenuecat.com](https://rev.cat/in-app-purchase-key-configuration)
         * for more info.
         *
         * - Note: StoreKit 2 is only available on iOS 16+. StoreKit 1 will be used for
         * previous iOS versions regardless of this setting.
         */
        public fun storeKitVersion(storeKitVersion: StoreKitVersion): Builder =
            apply { this.storeKitVersion = storeKitVersion }

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
            purchasesAreCompletedBy = purchasesAreCompletedBy,
            userDefaultsSuiteName = userDefaultsSuiteName,
            storeKitVersion = storeKitVersion,
            showInAppMessagesAutomatically = showInAppMessagesAutomatically,
            store = store,
            diagnosticsEnabled = diagnosticsEnabled,
            dangerousSettings = dangerousSettings,
            verificationMode = verificationMode
        )
    }
}

/**
 * Holds parameters to initialize the SDK. Create an instance of this class using the [builder] and
 * pass it to [Purchases.configure].
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
 * [sharedInstance][Purchases.sharedInstance].
 */
@JvmSynthetic
public fun Purchases.Companion.configure(
    apiKey: String,
    builder: Builder.() -> Unit = { }
): Purchases =
    configure(PurchasesConfiguration(apiKey, builder))
