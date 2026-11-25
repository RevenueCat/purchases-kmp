package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical

@Composable
internal fun InstallmentsInfoRow(
    info: InstallmentsInfo,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "installmentsInfo") },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "commitmentPaymentsCount: ${info.commitmentPaymentsCount}")
                Text(text = "renewalCommitmentPaymentsCount: ${info.renewalCommitmentPaymentsCount}")
                info.commitmentInstallmentPeriod
                    ?.let { PeriodText(period = it, label = "commitmentInstallmentPeriod") }
                    ?: Text(text = "commitmentInstallmentPeriod: null")
                PriceRow(price = info.installmentBillingPrice, label = "installmentBillingPrice")
                info.commitmentTotalPeriod
                    ?.let { PeriodText(period = it, label = "commitmentTotalPeriod") }
                    ?: Text(text = "commitmentTotalPeriod: null")
                PriceRow(price = info.commitmentTotalPrice, label = "commitmentTotalPrice")
                Text(text = "billingPlanType: ${info.billingPlanType}")
            }
        },
    )
}
