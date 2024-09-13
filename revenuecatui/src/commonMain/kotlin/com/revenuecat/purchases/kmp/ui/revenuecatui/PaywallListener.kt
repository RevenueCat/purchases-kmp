package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreTransaction

/**
 * Provide an implementation of this interface to [PaywallOptions] to listen for Paywall events.
 * Every function has a default no-op implementation.
 */
public interface PaywallListener {
    /**
     * Callback that gets called when a purchase is started.
     *
     * @param rcPackage The package being purchased.
     */
    public fun onPurchaseStarted(rcPackage: Package) {}

    /**
     * Callback that gets called when a purchase is completed.
     */
    public fun onPurchaseCompleted(
        customerInfo: CustomerInfo,
        storeTransaction: StoreTransaction
    ) {
    }

    /**
     * Callback that gets called when a purchase fails.
     */
    public fun onPurchaseError(error: PurchasesError) {}

    /**
     * Callback that gets called when a purchase is cancelled.
     */
    public fun onPurchaseCancelled() {}

    /**
     * Callback that gets called when a restore is started.
     */
    public fun onRestoreStarted() {}

    /**
     * Callback that gets called when a restore is completed. Note that this may get called even
     * if no entitlements have been granted in case no relevant purchases were found.
     */
    public fun onRestoreCompleted(customerInfo: CustomerInfo) {}

    /**
     * Callback that gets called when a restore fails.
     */
    public fun onRestoreError(error: PurchasesError) {}
}
