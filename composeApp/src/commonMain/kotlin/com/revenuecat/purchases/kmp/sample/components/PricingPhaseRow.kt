package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.PricingPhase
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun PricingPhasesRow(
    phases: List<PricingPhase>,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "pricingPhases: ${phases.size}") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                phases.forEach { phase ->
                    PricingPhaseRow(phase = phase)
                }
            }
        },
    )
}

@Composable
internal fun PricingPhaseRow(
    phase: PricingPhase,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "${phase.price.formatted} / ${phase.billingPeriod.value} ${phase.billingPeriod.unit}") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                Text(text = "recurrenceMode: ${phase.recurrenceMode}")
                Text(text = "offerPaymentMode: ${phase.offerPaymentMode}")
                Text(text = "billingCycleCount: ${phase.billingCycleCount}")
                PriceRow(price = phase.price)
                PeriodText(period = phase.billingPeriod, label = "billingPeriod")
            }
        },
    )
}
