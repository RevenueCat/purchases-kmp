package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogic
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogicParams
import com.revenuecat.purchases.kmp.ui.revenuecatui.PurchaseLogicResult


@Suppress("unused", "UNUSED_VARIABLE", "RedundantExplicitType")
private class PaywallPurchaseLogicAPI {
    suspend fun check(offering: Offering?, rcPackage: Package, customerInfo: CustomerInfo) {
        val purchaseLogic = object : PaywallPurchaseLogic {
            override suspend fun performPurchase(params: PaywallPurchaseLogicParams): PurchaseLogicResult {
                val pkg: Package = params.rcPackage
                val oldProductId: String? = params.oldProductId
                val replacementMode: com.revenuecat.purchases.kmp.models.ReplacementMode? = params.replacementMode
                val subscriptionOption: SubscriptionOption? = params.subscriptionOption
                return PurchaseLogicResult.Success()
            }

            override suspend fun performRestore(customerInfo: CustomerInfo): PurchaseLogicResult {
                return PurchaseLogicResult.Success()
            }
        }

        // PurchaseLogicResult types
        val successResult: PurchaseLogicResult = PurchaseLogicResult.Success()
        val cancellationResult: PurchaseLogicResult = PurchaseLogicResult.Cancellation()
        val errorResult: PurchaseLogicResult = PurchaseLogicResult.Error()
        val errorWithDetails: PurchaseLogicResult = PurchaseLogicResult.Error(
            PurchasesError(PurchasesErrorCode.UnknownError, "Something went wrong")
        )
        val errorDetails: PurchasesError? = (errorWithDetails as PurchaseLogicResult.Error).errorDetails

        // PaywallPurchaseLogicParams.Builder
        val params: PaywallPurchaseLogicParams = PaywallPurchaseLogicParams.Builder(rcPackage)
            .oldProductId("com.example.old_product")
            .replacementMode(GoogleReplacementMode.CHARGE_PRORATED_PRICE)
            .subscriptionOption(null)
            .build()

        val options: PaywallOptions = PaywallOptions(
            dismissRequest = {}
        ) {
            this.offering = offering
            this.purchaseLogic = purchaseLogic
        }
        val logic: PaywallPurchaseLogic? = options.purchaseLogic
    }
}
