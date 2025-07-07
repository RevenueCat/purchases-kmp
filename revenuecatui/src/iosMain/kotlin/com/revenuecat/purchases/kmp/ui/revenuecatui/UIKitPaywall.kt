package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallFooterViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import com.revenuecat.purchases.kmp.mappings.toIosOffering
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.layoutViewController
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.rememberLayoutViewControllerState
import objcnames.classes.RCOffering

@Composable
internal fun UIKitPaywall(
    options: PaywallOptions,
    footer: Boolean,
    modifier: Modifier = Modifier,
) {
    val layoutViewControllerState = rememberLayoutViewControllerState()

    // Keeping references to avoid them being deallocated.
    val dismissRequestedHandler: (RCPaywallViewController?) -> Unit =
        remember(options.dismissRequest) { { options.dismissRequest() } }
    val delegate = remember(options.listener) {
        IosPaywallDelegate(
            listener = options.listener,
            onHeightChange = { layoutViewControllerState.updateIntrinsicContentHeight() }
        )
    }

    UIKitViewController(
        modifier = modifier.layoutViewController(layoutViewControllerState),
        factory = {
            val paywallViewController = if (footer) RCPaywallFooterViewController(
                offering = options.offering?.toIosOffering() as? RCOffering,
                displayCloseButton = options.shouldDisplayDismissButton,
                shouldBlockTouchEvents = false,
                dismissRequestedHandler = dismissRequestedHandler,
            ) else RCPaywallViewController(
                offering = options.offering?.toIosOffering() as? RCOffering,
                displayCloseButton = options.shouldDisplayDismissButton,
                shouldBlockTouchEvents = false,
                dismissRequestedHandler = dismissRequestedHandler,
            )

            paywallViewController
                .apply { setDelegate(delegate) }
                .also { layoutViewControllerState.setViewController(it) }
        },
        properties = uiKitInteropPropertiesNonExperimental(
            nonCooperativeInteractionMode = true,
            isNativeAccessibilityEnabled = true,
        )
    )
}
