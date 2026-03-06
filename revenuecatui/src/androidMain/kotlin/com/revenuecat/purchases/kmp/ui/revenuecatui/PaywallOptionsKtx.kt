package com.revenuecat.purchases.kmp.ui.revenuecatui

import android.app.Activity
import com.revenuecat.purchases.CustomerInfo as AndroidCustomerInfo
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.PurchasesErrorCode
import com.revenuecat.purchases.kmp.mappings.toAndroidOffering
import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toPackage
import com.revenuecat.purchases.kmp.mappings.toPurchasesError
import com.revenuecat.purchases.kmp.mappings.toStoreTransaction
import com.revenuecat.purchases.kmp.mappings.toSubscriptionOption
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.models.GoogleReplacementMode as AndroidGoogleReplacementMode
import com.revenuecat.purchases.models.StoreTransaction
import com.revenuecat.purchases.ui.revenuecatui.PaywallPurchaseLogic as AndroidPaywallPurchaseLogic
import com.revenuecat.purchases.ui.revenuecatui.PaywallPurchaseLogicParams as AndroidPaywallPurchaseLogicParams
import com.revenuecat.purchases.ui.revenuecatui.PurchaseLogicResult as AndroidPurchaseLogicResult
import com.revenuecat.purchases.ui.revenuecatui.PaywallListener as AndroidPaywallListener
import com.revenuecat.purchases.ui.revenuecatui.PaywallOptions as AndroidPaywallOptions

internal fun PaywallOptions.toAndroidPaywallOptions(): AndroidPaywallOptions =
    AndroidPaywallOptions.Builder(dismissRequest)
        .setOffering(offering?.toAndroidOffering())
        .setShouldDisplayDismissButton(shouldDisplayDismissButton)
        .setListener(listener?.toAndroidPaywallListener())
        .setPurchaseLogic(purchaseLogic?.toAndroidPurchaseLogic())
        .build()

private fun PaywallPurchaseLogic.toAndroidPurchaseLogic(): AndroidPaywallPurchaseLogic =
    PurchaseLogicWrapper(this)

private class PurchaseLogicWrapper(
    private val purchaseLogic: PaywallPurchaseLogic
) : AndroidPaywallPurchaseLogic {
    override suspend fun performPurchase(
        activity: Activity,
        params: AndroidPaywallPurchaseLogicParams
    ): AndroidPurchaseLogicResult {
        val kmpParams = PaywallPurchaseLogicParams(
            rcPackage = params.rcPackage.toPackage(),
            oldProductId = params.oldProductId,
            replacementMode = (params.replacementMode as? AndroidGoogleReplacementMode)
                ?.toGoogleReplacementMode(),
            subscriptionOption = params.subscriptionOption?.toSubscriptionOption(),
        )
        return when (val result = purchaseLogic.performPurchase(kmpParams)) {
            is PurchaseLogicResult.Success -> AndroidPurchaseLogicResult.Success
            is PurchaseLogicResult.Cancellation -> AndroidPurchaseLogicResult.Cancellation
            is PurchaseLogicResult.Error -> AndroidPurchaseLogicResult.Error(
                result.errorDetails?.toAndroidPurchasesError()
            )
            else -> AndroidPurchaseLogicResult.Error(null)
        }
    }

    override suspend fun performRestore(
        customerInfo: AndroidCustomerInfo
    ): AndroidPurchaseLogicResult {
        return when (val result = purchaseLogic.performRestore(customerInfo.toCustomerInfo())) {
            is PurchaseLogicResult.Success -> AndroidPurchaseLogicResult.Success
            is PurchaseLogicResult.Cancellation -> AndroidPurchaseLogicResult.Cancellation
            is PurchaseLogicResult.Error -> AndroidPurchaseLogicResult.Error(
                result.errorDetails?.toAndroidPurchasesError()
            )
            else -> AndroidPurchaseLogicResult.Error(null)
        }
    }
}

private fun AndroidGoogleReplacementMode.toGoogleReplacementMode(): GoogleReplacementMode =
    when (this) {
        AndroidGoogleReplacementMode.WITHOUT_PRORATION -> GoogleReplacementMode.WITHOUT_PRORATION
        AndroidGoogleReplacementMode.WITH_TIME_PRORATION -> GoogleReplacementMode.WITH_TIME_PRORATION
        AndroidGoogleReplacementMode.CHARGE_PRORATED_PRICE -> GoogleReplacementMode.CHARGE_PRORATED_PRICE
        AndroidGoogleReplacementMode.CHARGE_FULL_PRICE -> GoogleReplacementMode.CHARGE_FULL_PRICE
        AndroidGoogleReplacementMode.DEFERRED -> GoogleReplacementMode.DEFERRED
    }

private fun com.revenuecat.purchases.kmp.models.PurchasesError.toAndroidPurchasesError(): PurchasesError =
    PurchasesError(
        PurchasesErrorCode.UnknownError,
        underlyingErrorMessage ?: code.description,
    )

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
