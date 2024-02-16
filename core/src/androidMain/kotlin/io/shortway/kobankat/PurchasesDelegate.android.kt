package io.shortway.kobankat

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.interfaces.UpdatedCustomerInfoListener

internal fun UpdatedCustomerInfoListener.toPurchasesDelegate(): PurchasesDelegate =
    (this as PurchasesDelegateWrapper).wrapped

internal fun PurchasesDelegate.toUpdatedCustomerInfoListener(): UpdatedCustomerInfoListener =
    PurchasesDelegateWrapper(this)

private class PurchasesDelegateWrapper(val wrapped: PurchasesDelegate) :
    UpdatedCustomerInfoListener {
    override fun onReceived(customerInfo: CustomerInfo) {
        wrapped.onCustomerInfoUpdated(customerInfo)
    }
}