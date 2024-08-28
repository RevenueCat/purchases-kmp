package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.models.InstallmentsInfo as AndroidInstallmentsInfo

public fun AndroidInstallmentsInfo.toInstallmentsInfo(): InstallmentsInfo =
    InstallmentsInfo(
        commitmentPaymentsCount = this.commitmentPaymentsCount,
        renewalCommitmentPaymentsCount = this.renewalCommitmentPaymentsCount,
    )
