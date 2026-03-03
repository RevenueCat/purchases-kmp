package com.revenuecat.purchases.kmp.sample

import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallPurchaseLogic
import com.revenuecat.purchases.kmp.ui.revenuecatui.PurchaseLogicResult
import kotlinx.coroutines.delay

internal class SamplePurchaseLogic : PaywallPurchaseLogic {
    override suspend fun performPurchase(rcPackage: Package): PurchaseLogicResult {
        println("[SamplePurchaseLogic] performPurchase called for: ${rcPackage.identifier}")
        delay(2000)
        println("[SamplePurchaseLogic] performPurchase returning success")
        return PurchaseLogicResult.Success
    }

    override suspend fun performRestore(): PurchaseLogicResult {
        println("[SamplePurchaseLogic] performRestore called")
        delay(2000)
        println("[SamplePurchaseLogic] performRestore returning success")
        return PurchaseLogicResult.Success
    }
}
