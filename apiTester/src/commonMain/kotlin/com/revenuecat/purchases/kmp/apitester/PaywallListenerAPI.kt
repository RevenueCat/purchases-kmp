package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallListener


@Suppress("unused", "UNUSED_VARIABLE", "EmptyFunctionBlock", "RedundantOverride")
private class PaywallListenerAPI {
    fun check() {
        val listener = object : PaywallListener {
            override fun onPurchaseStarted(rcPackage: Package) {
                super.onPurchaseStarted(rcPackage)
            }

            override fun onPurchaseCompleted(
                customerInfo: CustomerInfo,
                storeTransaction: StoreTransaction
            ) {
                super.onPurchaseCompleted(customerInfo, storeTransaction)
            }

            override fun onPurchaseError(error: PurchasesError) {
                super.onPurchaseError(error)
            }

            override fun onPurchaseCancelled() {
                super.onPurchaseCancelled()
            }

            override fun onRestoreStarted() {
                super.onRestoreStarted()
            }

            override fun onRestoreCompleted(customerInfo: CustomerInfo) {
                super.onRestoreCompleted(customerInfo)
            }

            override fun onRestoreError(error: PurchasesError) {
                super.onRestoreError(error)
            }
        }
    }
}
