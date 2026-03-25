package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
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

        val options: PaywallOptions = PaywallOptions(
            dismissRequest = {}
        ) {
            this.offering = offering
            this.purchaseLogic = purchaseLogic
        }
        val logic: PaywallPurchaseLogic? = options.purchaseLogic
    }
}
