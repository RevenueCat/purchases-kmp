package com.revenuecat.maestro.e2e

actual fun presentPaywallNatively(onDismiss: () -> Unit) {
    // On Android the Compose Paywall composable works fine with Maestro,
    // so this is a no-op; the common code falls back to inline rendering.
}
