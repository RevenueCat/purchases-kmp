package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.CustomerInfo as AndroidCustomerInfo
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.kmp.mappings.toAndroidOffering
import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toPackage
import com.revenuecat.purchases.kmp.mappings.toPurchasesError
import com.revenuecat.purchases.kmp.mappings.toStoreTransaction
import com.revenuecat.purchases.models.StoreTransaction
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.PaywallListener as AndroidPaywallListener
import com.revenuecat.purchases.ui.revenuecatui.PaywallOptions as AndroidPaywallOptions

internal fun PaywallOptions.toAndroidPaywallOptions(): AndroidPaywallOptions =
    AndroidPaywallOptions.Builder(dismissRequest)
        .setOffering(offering?.toAndroidOffering())
        .setShouldDisplayDismissButton(shouldDisplayDismissButton)
        .setListener(listener?.toAndroidPaywallListener())
        .build()

private fun PaywallListener.toAndroidPaywallListener(): AndroidPaywallListener =
    PaywallListenerWrapper(this)

private class PaywallListenerWrapper(private val listener: PaywallListener) :
    AndroidPaywallListener {
    override fun onPurchaseCancelled() =
        listener.onPurchaseCancelled()

    override fun onPurchaseCompleted(
        customerInfo: AndroidCustomerInfo,
        storeTransaction: StoreTransaction
    ) = listener.onPurchaseCompleted(customerInfo.toCustomerInfo(), storeTransaction.toStoreTransaction())

    override fun onPurchaseError(error: PurchasesError) =
        listener.onPurchaseError(error.toPurchasesError())

    override fun onPurchaseStarted(rcPackage: Package) =
        listener.onPurchaseStarted(rcPackage.toPackage())

    override fun onRestoreCompleted(customerInfo: AndroidCustomerInfo) =
        listener.onRestoreCompleted(customerInfo.toCustomerInfo())

    override fun onRestoreError(error: PurchasesError) =
        listener.onRestoreError(error.toPurchasesError())

    override fun onRestoreStarted() = listener.onRestoreStarted()
}
