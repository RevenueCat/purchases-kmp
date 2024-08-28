package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.InstallmentsInfo

@Suppress("unused", "UNUSED_VARIABLE")
private class InstallmentsInfoAPI {
    fun checkInstallmentsInfo(installmentsInfo: InstallmentsInfo) {
        val commitmentPaymentsCount: Int = installmentsInfo.commitmentPaymentsCount
        val renewalCommitmentPaymentsCount: Int = installmentsInfo.renewalCommitmentPaymentsCount
    }
}
