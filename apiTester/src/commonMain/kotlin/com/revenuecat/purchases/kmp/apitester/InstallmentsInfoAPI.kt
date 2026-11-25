package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.BillingPlanType
import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.Price

@Suppress("unused", "UNUSED_VARIABLE")
private class InstallmentsInfoAPI {
    fun checkConstructor(
        commitmentPaymentsCount: Int,
        renewalCommitmentPaymentsCount: Int,
        commitmentInstallmentPeriod: Period?,
        installmentBillingPrice: Price?,
        commitmentTotalPeriod: Period?,
        commitmentTotalPrice: Price?,
        billingPlanType: BillingPlanType?,
    ) {
        val installmentsInfo1 = InstallmentsInfo(
            commitmentPaymentsCount = commitmentPaymentsCount,
            renewalCommitmentPaymentsCount = renewalCommitmentPaymentsCount,
        )

        val installmentsInfo2 = InstallmentsInfo(
            commitmentPaymentsCount = commitmentPaymentsCount,
            renewalCommitmentPaymentsCount = renewalCommitmentPaymentsCount,
            commitmentInstallmentPeriod = commitmentInstallmentPeriod,
            installmentBillingPrice = installmentBillingPrice,
            commitmentTotalPeriod = commitmentTotalPeriod,
            commitmentTotalPrice = commitmentTotalPrice,
            billingPlanType = billingPlanType,
        )
    }

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
