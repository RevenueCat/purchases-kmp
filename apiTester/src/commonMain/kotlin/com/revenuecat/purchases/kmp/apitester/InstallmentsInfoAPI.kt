package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.BillingPlanType
import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.Price

@Suppress("unused", "UNUSED_VARIABLE")
private class InstallmentsInfoAPI {
    fun checkInstallmentsInfo(installmentsInfo: InstallmentsInfo) {
        val commitmentPaymentsCount: Int = installmentsInfo.commitmentPaymentsCount
        val renewalCommitmentPaymentsCount: Int = installmentsInfo.renewalCommitmentPaymentsCount
        val commitmentInstallmentPeriod: Period? = installmentsInfo.commitmentInstallmentPeriod
        val installmentBillingPrice: Price? = installmentsInfo.installmentBillingPrice
        val commitmentTotalPeriod: Period? = installmentsInfo.commitmentTotalPeriod
        val commitmentTotalPrice: Price? = installmentsInfo.commitmentTotalPrice
        val billingPlanType: BillingPlanType? = installmentsInfo.billingPlanType
    }
}
