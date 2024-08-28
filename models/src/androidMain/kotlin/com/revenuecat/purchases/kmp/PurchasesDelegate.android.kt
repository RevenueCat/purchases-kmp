package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction

/**
 * A convenience implementation of [PurchasesDelegate] specifically for Android. Only requires the
 * caller to provide an implementation of [onCustomerInfoUpdated], just like
 * `UpdatedCustomerInfoListener` in the official RevenueCat Android SDK.
 */
public class UpdatedCustomerInfoDelegate(
    private val onCustomerInfoUpdated: (customerInfo: CustomerInfo) -> Unit
) : PurchasesDelegate {
    override fun onCustomerInfoUpdated(customerInfo: CustomerInfo): Unit =
        onCustomerInfoUpdated.invoke(customerInfo)

    override fun onPurchasePromoProduct(
        product: StoreProduct,
        startPurchase: (onError: (error: PurchasesError, userCancelled: Boolean) -> Unit, onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit) -> Unit
    ) {
        // No-op on Android.
    }
}
