package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.kmp.toPurchasesError
import com.revenuecat.purchases.models.StoreTransaction
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.PaywallListener as AndroidPaywallListener
import com.revenuecat.purchases.ui.revenuecatui.PaywallOptions as AndroidPaywallOptions

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
internal fun PaywallOptions.toAndroidPaywallOptions(): AndroidPaywallOptions =
    AndroidPaywallOptions.Builder(dismissRequest)
        .setOffering(offering)
        .setShouldDisplayDismissButton(shouldDisplayDismissButton)
        .setListener(listener?.toAndroidPaywallListener())
        .build()

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
private fun PaywallListener.toAndroidPaywallListener(): AndroidPaywallListener =
    PaywallListenerWrapper(this)

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
private class PaywallListenerWrapper(private val listener: PaywallListener) :
    AndroidPaywallListener {
    override fun onPurchaseCancelled() =
        listener.onPurchaseCancelled()

    override fun onPurchaseCompleted(
        customerInfo: CustomerInfo,
        storeTransaction: StoreTransaction
    ) = listener.onPurchaseCompleted(customerInfo, storeTransaction)

    override fun onPurchaseError(error: PurchasesError) =
        listener.onPurchaseError(error.toPurchasesError())

    override fun onPurchaseStarted(rcPackage: Package) =
        listener.onPurchaseStarted(rcPackage)

    override fun onRestoreCompleted(customerInfo: CustomerInfo) =
        listener.onRestoreCompleted(customerInfo)

    override fun onRestoreError(error: PurchasesError) =
        listener.onRestoreError(error.toPurchasesError())

    override fun onRestoreStarted() = listener.onRestoreStarted()
}
