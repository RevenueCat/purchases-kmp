package com.revenuecat.maestro.e2e

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
actual fun PaywallPresenter(onDismiss: () -> Unit) {
    val options = remember {
        PaywallOptions(dismissRequest = onDismiss) {
            shouldDisplayDismissButton = true
        }
    }
    Paywall(options)
}
