package com.revenuecat.maestro.e2e

import androidx.compose.runtime.Composable

@Composable
expect fun PaywallPresenter(onDismiss: () -> Unit)
