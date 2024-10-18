package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
    val paywallComposable = @Composable {
        UIKitPaywall(
            options = options,
            footer = true,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .wrapContentHeight()
                .clip(
                    RoundedCornerShape(topStart = DefaultCornerRadius, topEnd = DefaultCornerRadius)
                ),
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
