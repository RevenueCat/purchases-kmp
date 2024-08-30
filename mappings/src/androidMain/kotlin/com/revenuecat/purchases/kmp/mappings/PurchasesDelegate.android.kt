package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.interfaces.UpdatedCustomerInfoListener
import com.revenuecat.purchases.kmp.PurchasesDelegate

public fun UpdatedCustomerInfoListener.toPurchasesDelegate(): PurchasesDelegate =
    (this as PurchasesDelegateWrapper).wrapped

public fun PurchasesDelegate.toUpdatedCustomerInfoListener(): UpdatedCustomerInfoListener =
    PurchasesDelegateWrapper(this)

private class PurchasesDelegateWrapper(val wrapped: PurchasesDelegate) :
    UpdatedCustomerInfoListener {
    override fun onReceived(customerInfo: CustomerInfo) {
        wrapped.onCustomerInfoUpdated(customerInfo.toCustomerInfo())
    }
}
