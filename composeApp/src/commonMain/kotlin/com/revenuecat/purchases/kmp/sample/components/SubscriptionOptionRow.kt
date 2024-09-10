package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.SubscriptionOptions
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical

@Composable
internal fun SubscriptionOptionsRow(
    options: SubscriptionOptions,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "subscriptionOptions") },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                options.basePlan?.let {
                    SubscriptionOptionRow(option = it, label = "basePlan")
                } ?: Text(text = "basePlan: null")
                options.freeTrial?.let {
                    SubscriptionOptionRow(option = it, label = "freeTrial")
                } ?: Text(text = "freeTrial: null")
                options.introOffer?.let {
                    SubscriptionOptionRow(option = it, label = "introOffer")
                } ?: Text(text = "introOffer: null")
                options.defaultOffer?.let {
                    SubscriptionOptionRow(option = it, label = "defaultOffer")
                } ?: Text(text = "defaultOffer: null")
            }
        },
    )
}

@Composable
internal fun SubscriptionOptionRow(
    option: SubscriptionOption,
    modifier: Modifier = Modifier,
    label: String = "option",
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "$label: ${option.id}") },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                PurchasingDataRow(data = option.purchasingData)
                option.presentedOfferingContext?.let {
                    PresentedOfferingContextRow(context = it)
                } ?: Text(text = "presentedOfferingContext: null")
                option.installmentsInfo?.let {
                    InstallmentsInfoRow(info = it)
                } ?: Text(text = "installmentsInfo: null")
                PricingPhasesRow(phases = option.pricingPhases)
                CollapsibleStringsRow(
                    strings = option.tags,
                    label = "tags",
                )
            }
        },
    )
}
