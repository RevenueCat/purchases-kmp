package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import objcnames.classes.RCOffering

@Composable
public actual fun Paywall(options: PaywallOptions) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            RCPaywallViewController(
                offering = options.offering as RCOffering,
                displayCloseButton = false,
                shouldBlockTouchEvents = true,
                dismissRequestedHandler = { options.dismissRequest() }
            ).apply { setDelegate(PaywallViewControllerDelegate(options.onRestoreCompleted)) }
                .view
        }
    )
}
