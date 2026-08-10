package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.RewardVerificationToken

@Suppress("unused", "UNUSED_VARIABLE")
private class RewardVerificationTokenAPI {
    @OptIn(ExperimentalRevenueCatApi::class)
    fun check(token: RewardVerificationToken) {
        val customData: String = token.customData
        val clientTransactionId: String = token.clientTransactionId
        val appUserID: String = token.appUserID
    }
}
