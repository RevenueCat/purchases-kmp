package com.revenuecat.purchases.kmp

/**
 * Modes for completing the purchase process.
 */
public sealed class PurchasesAreCompletedBy {

    /**
     * RevenueCat will automatically acknowledge verified purchases.
     * No action is required by you.
     */
    public data object RevenueCat : PurchasesAreCompletedBy()

    /**
     * RevenueCat will **not** automatically acknowledge any purchases.
     * You will have to do so manually.
     * Note that failing to acknowledge a purchase within 3 days will lead
     * to Google Play automatically issuing a refund to the user.
     *
     * For more info, see [revenuecat.com](https://www.revenuecat.com/docs/migrating-to-revenuecat/sdk-or-not/finishing-transactions)
     * and [developer.android.com](https://developer.android.com/google/play/billing/integrate#process).
     *
     * @property storeKitVersion The version of StoreKit to use for purchases on Apple devices.
     * If your app is Android-only, you may provide any value since it is ignored in the native
     * Android SDK.
     */
    public data class MyApp(val storeKitVersion: StoreKitVersion) : PurchasesAreCompletedBy()
}

/**
 * Convenience function to get the storeKitVersion.
 *
 * @return the storeKitVersion if the instance is MyApp, otherwise returns StoreKitVersion.DEFAULT
 */
internal fun PurchasesAreCompletedBy.storeKitVersion(): StoreKitVersion =
    when (this) {
        is PurchasesAreCompletedBy.MyApp -> this.storeKitVersion
        is PurchasesAreCompletedBy.RevenueCat -> StoreKitVersion.DEFAULT
    }
