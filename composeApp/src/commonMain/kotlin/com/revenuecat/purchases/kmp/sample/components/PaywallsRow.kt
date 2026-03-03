package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.purchasesAreCompletedByMyApp

@Composable
internal fun PaywallsRow(
    onShowFullscreenClick: () -> Unit,
    onShowFooterClick: () -> Unit,
    onShowFullscreenWithPurchaseLogicClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = { Text(text = "Paywalls") },
        expandedContent = {
            Column(modifier = Modifier.padding(horizontal = DefaultPaddingHorizontal)) {
                Row {
                    TextButton(
                        onClick = onShowFooterClick,
                        modifier = Modifier.weight(1f, fill = true)
                    ) {
                        Text("Footer")
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    TextButton(
                        onClick = onShowFullscreenClick,
                        modifier = Modifier.weight(1f, fill = true)
                    ) {
                        Text("Fullscreen")
                    }
                }
                TextButton(
                    onClick = onShowFullscreenWithPurchaseLogicClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = purchasesAreCompletedByMyApp,
                ) {
                    Text(
                        text = if (purchasesAreCompletedByMyApp) {
                            "Fullscreen with PurchaseLogic"
                        } else {
                            "Fullscreen with PurchaseLogic\n(Enable purchasesAreCompletedByMyApp)"
                        },
                        textAlign = TextAlign.Center,
                    )
                }
            }
        },
        modifier = modifier,
    )
}
