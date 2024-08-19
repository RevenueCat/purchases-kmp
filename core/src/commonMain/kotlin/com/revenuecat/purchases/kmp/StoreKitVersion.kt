package com.revenuecat.purchases.kmp

/**
 * Defines which version of StoreKit may be used.
 */
public enum class StoreKitVersion {
    /**
     * Always use StoreKit 1.
     */
    STOREKIT_1,

    /**
     * Always use StoreKit 2 (StoreKit 1 will be used if StoreKit 2
     * is not available in the current device.)
     *
     * - Warning: Make sure you have an In-App Purchase Key configured in your app.
     * Please see https://rev.cat/in-app-purchase-key-configuration for more info.
     */
    STOREKIT_2,

    /**
     * Let RevenueCat use the most appropriate version of StoreKit
     */
    DEFAULT,
}

internal fun StoreKitVersion.toHybridString(): String =
    when(this) {
        StoreKitVersion.STOREKIT_1 -> "STOREKIT_1"
        StoreKitVersion.STOREKIT_2 -> "STOREKIT_2"
        StoreKitVersion.DEFAULT -> "DEFAULT"
    }