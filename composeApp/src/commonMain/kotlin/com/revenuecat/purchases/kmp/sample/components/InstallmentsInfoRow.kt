package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun InstallmentsInfoRow(
    info: InstallmentsInfo,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "installmentsInfo") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                Text(text = "commitmentPaymentsCount: ${info.commitmentPaymentsCount}")
                Text(text = "renewalCommitmentPaymentsCount: ${info.renewalCommitmentPaymentsCount}")
            }
        },
    )
}
