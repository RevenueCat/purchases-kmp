package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public actual fun CustomerCenter(
    onDismiss: () -> Unit
): Unit =
    UIKitCustomerCenter(
        modifier = Modifier.fillMaxSize(),
        onDismiss = onDismiss
    )
