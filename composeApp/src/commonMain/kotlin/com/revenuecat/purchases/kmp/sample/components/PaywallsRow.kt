package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun PaywallsRow(
    onShowFullscreenClick: () -> Unit,
    onShowFooterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = { Text(text = "Paywalls") },
        expandedContent = {
            Row(modifier = Modifier.padding(horizontal = DefaultPaddingHorizontal)) {
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
        },
        modifier = modifier,
    )
}
