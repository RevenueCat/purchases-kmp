package com.revenuecat.purchases.kmp.sample

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogic
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogicParams
import com.revenuecat.purchases.kmp.ui.revenuecatui.PurchaseLogicResult
import kotlinx.coroutines.delay

internal class SamplePurchaseLogic : PaywallPurchaseLogic {
    override suspend fun performPurchase(params: PaywallPurchaseLogicParams): PurchaseLogicResult {
        println("[SamplePurchaseLogic] performPurchase called for: ${params.rcPackage.identifier}")
        delay(2000)
        println("[SamplePurchaseLogic] performPurchase returning success")
        return PurchaseLogicResult.Success()
    }

    override suspend fun performRestore(customerInfo: CustomerInfo): PurchaseLogicResult {
        println("[SamplePurchaseLogic] performRestore called")
        delay(2000)
        println("[SamplePurchaseLogic] performRestore returning success")
        return PurchaseLogicResult.Success()
    }
}
