package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.PresentedOfferingContext
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical

@Composable
internal fun PresentedOfferingContextRow(
    context: PresentedOfferingContext,
    modifier: Modifier = Modifier,
) {

    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "presentedOfferingContext") },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "offeringIdentifier: ${context.offeringIdentifier}")
                Text(text = "placementIdentifier: ${context.placementIdentifier}")
                context.targetingContext?.let {
                    PresentedOfferingTargetingContextRow(context = it)
                } ?: Text(text = "presentedOfferingTargetingContext: null")
            }
        },
    )
}
