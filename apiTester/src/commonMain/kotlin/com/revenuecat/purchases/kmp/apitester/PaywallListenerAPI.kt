package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallInteractionEvent
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

            override fun onWebCheckoutOpened() {
                super.onWebCheckoutOpened()
            }

            override fun onUrlOpened(url: String) {
                super.onUrlOpened(url)
            }

            override fun onInteraction(event: PaywallInteractionEvent) {
                super.onInteraction(event)
                val rawProperties: Map<String, Any> = event.rawProperties
                val componentType: String? = event.getProperty(PaywallInteractionEvent.Keys.COMPONENT_TYPE)
                val revision: Int? = event.getProperty(PaywallInteractionEvent.Keys.PAYWALL_REVISION)
                val timestamp: Long? = event.getProperty(PaywallInteractionEvent.Keys.TIMESTAMP)
                val darkMode: Boolean? = event.getProperty(PaywallInteractionEvent.Keys.DARK_MODE)
                val tab: String = PaywallInteractionEvent.ComponentTypes.TAB
            }
        }
    }
}
