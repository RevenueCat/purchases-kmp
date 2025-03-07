package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public actual fun CustomerCenter(
    modifier: Modifier,
    onDismiss: () -> Unit
): Unit =
    UIKitCustomerCenter(
        modifier = modifier,
        onDismiss = onDismiss
    )
