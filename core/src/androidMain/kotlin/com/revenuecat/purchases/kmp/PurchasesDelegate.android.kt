package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.interfaces.UpdatedCustomerInfoListener
import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.CustomerInfo as RCCustomerInfo

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

internal fun UpdatedCustomerInfoListener.toPurchasesDelegate(): PurchasesDelegate =
    (this as PurchasesDelegateWrapper).wrapped

internal fun PurchasesDelegate.toUpdatedCustomerInfoListener(): UpdatedCustomerInfoListener =
    PurchasesDelegateWrapper(this)

private class PurchasesDelegateWrapper(val wrapped: PurchasesDelegate) :
    UpdatedCustomerInfoListener {
    override fun onReceived(customerInfo: RCCustomerInfo) {
        wrapped.onCustomerInfoUpdated(customerInfo.toCustomerInfo())
    }
}
