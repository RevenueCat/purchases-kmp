package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.PresentedOfferingTargetingContext
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun PresentedOfferingTargetingContextRow(
    context: PresentedOfferingTargetingContext,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = {
            Text(text = "presentedOfferingTargetingContext")
        },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                Text(text = "ruleId: ${context.ruleId}")
                Text(text = "revision: ${context.revision}")
            }
        },
    )
}
