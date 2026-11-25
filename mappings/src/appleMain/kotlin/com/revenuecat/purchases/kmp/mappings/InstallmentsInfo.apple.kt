package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.kn.core.additional.InstallmentsInfoPrices
import com.revenuecat.purchases.kn.core.RCInstallmentsInfo as IosInstallmentsInfo

internal fun IosInstallmentsInfo.toInstallmentsInfo(currencyCode: String): InstallmentsInfo =
    InstallmentsInfo(
        commitmentPaymentsCount = commitmentInstallmentsCount().toInt(),
        renewalCommitmentPaymentsCount = commitmentInstallmentsCount().toInt(),
        commitmentInstallmentPeriod = commitmentInstallmentPeriod().toPeriod(),
        installmentBillingPrice = priceOrNull(
            currencyCode = currencyCode,
            formatted = installmentBillingDisplayPrice(),
            amountDecimal = InstallmentsInfoPrices.installmentBillingPrice(this),
        ),
        commitmentTotalPeriod = commitmentTotalPeriod().toPeriod(),
        commitmentTotalPrice = priceOrNull(
            currencyCode = currencyCode,
            formatted = commitmentTotalDisplayPrice(),
            amountDecimal = InstallmentsInfoPrices.commitmentTotalPrice(this),
        ),
        billingPlanType = billingPlanType().toBillingPlanType(),
    )
