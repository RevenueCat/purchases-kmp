package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public actual fun Paywall(options: PaywallOptions): Unit =
    UIKitPaywall(
        options = options,
        footer = false,
        modifier = Modifier.fillMaxSize()
    )
