package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.Period

@Composable
internal fun PeriodText(
    period: Period,
    modifier: Modifier = Modifier,
    label: String = "period",
) {
    Text(
        text = "$label: ${period.value} ${period.unit} (${period.valueInMonths} months)",
        modifier = modifier,
    )
}
