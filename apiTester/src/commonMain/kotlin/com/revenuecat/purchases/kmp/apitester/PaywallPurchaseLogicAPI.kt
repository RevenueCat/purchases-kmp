package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogic
import com.revenuecat.purchases.kmp.ui.revenuecatui.PurchaseLogicResult


@Suppress("unused", "UNUSED_VARIABLE", "RedundantExplicitType")
private class PaywallPurchaseLogicAPI {
    suspend fun check(offering: Offering?) {
        val purchaseLogic = object : PaywallPurchaseLogic {
            override suspend fun performPurchase(rcPackage: Package): PurchaseLogicResult {
                return PurchaseLogicResult.Success
            }

            override suspend fun performRestore(): PurchaseLogicResult {
                return PurchaseLogicResult.Success
            }
        }

        val successResult: PurchaseLogicResult = PurchaseLogicResult.Success
        val cancellationResult: PurchaseLogicResult = PurchaseLogicResult.Cancellation
        val errorResult: PurchaseLogicResult = PurchaseLogicResult.Error()
        val errorWithMessage: PurchaseLogicResult = PurchaseLogicResult.Error("Something went wrong")
        val errorMessage: String? = (errorWithMessage as PurchaseLogicResult.Error).errorMessage

        val options: PaywallOptions = PaywallOptions(
            dismissRequest = {}
        ) {
            this.offering = offering
            this.purchaseLogic = purchaseLogic
        }
        val logic: PaywallPurchaseLogic? = options.purchaseLogic
    }
}
