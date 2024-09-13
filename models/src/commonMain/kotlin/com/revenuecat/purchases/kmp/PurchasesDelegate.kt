package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction

/**
 * A listener interface responsible for handling promotional product purchases (App Store only) and
 * changes to customer information.
 */
public interface PurchasesDelegate {

    /**
     * App Store only. Called when a user initiates a promotional in-app purchase from the
     * App Store. If your app is able to handle a purchase at the current time, run the deferment
     * block in this method. If the app is not in a state to make a purchase: cache the
     * [startPurchase] block, then call the [startPurchase] block when the app is ready to make the
     * promotional purchase.
     */
    public fun onPurchasePromoProduct(
        product: StoreProduct,
        startPurchase: (
            onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
            onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit
        ) -> Unit
    )

    /**
     * Called whenever [Purchases] receives updated customer info. This may happen periodically
     * throughout the life of the app if new information becomes available.
     */
    public fun onCustomerInfoUpdated(customerInfo: CustomerInfo)

}
