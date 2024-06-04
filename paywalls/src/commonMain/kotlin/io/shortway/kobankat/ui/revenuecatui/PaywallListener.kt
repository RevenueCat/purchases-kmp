package io.shortway.kobankat.ui.revenuecatui

import io.shortway.kobankat.CustomerInfo
import io.shortway.kobankat.Package
import io.shortway.kobankat.PurchasesError
import io.shortway.kobankat.models.StoreTransaction

public interface PaywallListener {
    public fun onPurchaseStarted(rcPackage: Package) {}
    public fun onPurchaseCompleted(
        customerInfo: CustomerInfo,
        storeTransaction: StoreTransaction
    ) {
    }

    public fun onPurchaseError(error: PurchasesError) {}
    public fun onPurchaseCancelled() {}
    public fun onRestoreStarted() {}
    public fun onRestoreCompleted(customerInfo: CustomerInfo) {}
    public fun onRestoreError(error: PurchasesError) {}
}
