package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * A Paywall footer.
 */
@Composable
public actual fun PaywallFooter(
    options: PaywallOptions,
    mainContent: @Composable ((PaddingValues) -> Unit)?,
) {
    var height by remember { mutableStateOf(0.dp) }
    val paywallComposable = @Composable {
        UIKitPaywall(
            options = options,
            footer = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = DefaultCornerRadius, topEnd = DefaultCornerRadius)
                )
                .height(height),
            onHeightChange = { newHeight -> height = newHeight.dp }
        )
    }

    // Largely the same as PaywallFooter in purchases:ui on Android.
    if (mainContent == null) {
        paywallComposable()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(-DefaultCornerRadius),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                mainContent(PaddingValues(bottom = DefaultCornerRadius))
            }
            paywallComposable()
        }
    }

}

private val DefaultCornerRadius = 20.dp
