package com.revenuecat.purchases.kmp.sample

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ktx.awaitPurchase
import com.revenuecat.purchases.kmp.ktx.awaitRestore
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.PurchasesException
import com.revenuecat.purchases.kmp.models.PurchasesTransactionException
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogic
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogicParams
import com.revenuecat.purchases.kmp.ui.revenuecatui.PurchaseLogicResult

internal class SamplePurchaseLogic : PaywallPurchaseLogic {
    override suspend fun performPurchase(params: PaywallPurchaseLogicParams): PurchaseLogicResult {
        println("[SamplePurchaseLogic] performPurchase called for: ${params.rcPackage.identifier}")
        return try {
            Purchases.sharedInstance.awaitPurchase(params.rcPackage)
            println("[SamplePurchaseLogic] performPurchase returning success")
            PurchaseLogicResult.Success()
        } catch (e: PurchasesTransactionException) {
            if (e.userCancelled) {
                println("[SamplePurchaseLogic] performPurchase cancelled by user")
                PurchaseLogicResult.Cancellation()
            } else {
                println("[SamplePurchaseLogic] performPurchase error: ${e.error}")
                PurchaseLogicResult.Error(e.error)
            }
        }
    }

    override suspend fun performRestore(customerInfo: CustomerInfo): PurchaseLogicResult {
        println("[SamplePurchaseLogic] performRestore called")
        return try {
            Purchases.sharedInstance.awaitRestore()
            println("[SamplePurchaseLogic] performRestore returning success")
            PurchaseLogicResult.Success()
        } catch (e: PurchasesException) {
            println("[SamplePurchaseLogic] performRestore error: ${e.error}")
            PurchaseLogicResult.Error(e.error)
        }
    }
}
