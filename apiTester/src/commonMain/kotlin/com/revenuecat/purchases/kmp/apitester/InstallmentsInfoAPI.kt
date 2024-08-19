package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.StoreKitVersion
import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.kmp.models.PricingPhase
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.isBasePlan
import com.revenuecat.purchases.kmp.models.isPrepaid

@Suppress("unused")
private class InstallmentsInfoAPI {
    fun checkInstallmentsInfo(installmentsInfo: InstallmentsInfo) {
        val commitmentPaymentsCount: Int = installmentsInfo.commitmentPaymentsCount
        val renewalCommitmentPaymentsCount: Int = installmentsInfo.renewalCommitmentPaymentsCount
    }
}
