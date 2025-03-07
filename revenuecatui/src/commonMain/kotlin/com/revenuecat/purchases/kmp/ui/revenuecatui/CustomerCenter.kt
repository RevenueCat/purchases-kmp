package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A full-screen CustomerCenter.
 */
@Composable
public expect fun CustomerCenter(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
)
