package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.PurchasesHybridCommonUI.RCPaywallFooterViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import objcnames.classes.RCOffering

@Composable
internal fun UIKitPaywall(
    options: PaywallOptions,
    footer: Boolean,
    modifier: Modifier = Modifier,
    onHeightChange: (Int) -> Unit = { },
) {
    // Keeping references to avoid them being deallocated.
    val dismissRequestedHandler: (RCPaywallViewController?) -> Unit =
        remember(options.dismissRequest) { { options.dismissRequest() } }
    val delegate = remember(options.listener, onHeightChange) {
        IosPaywallDelegate(options.listener, onHeightChange)
    }
    // We remember this wrapper so we can keep a reference to RCPaywallViewController, even during
    // recompositions. RCPaywallViewController itself is not yet instantiated here.
    val viewControllerWrapper = remember { ViewControllerWrapper(null) }
    UIKitView(
        modifier = modifier,
        factory = {
            val viewController = if (footer) RCPaywallFooterViewController(
                offering = options.offering as RCOffering,
                displayCloseButton = options.shouldDisplayDismissButton,
                shouldBlockTouchEvents = false,
                dismissRequestedHandler = dismissRequestedHandler,
            ) else RCPaywallViewController(
                offering = options.offering as RCOffering,
                displayCloseButton = options.shouldDisplayDismissButton,
                shouldBlockTouchEvents = false,
                dismissRequestedHandler = dismissRequestedHandler,
            )

            viewController.apply { setDelegate(delegate) }
                .also { viewControllerWrapper.value = it }
                .view
        },
    )
}

/**
 * Can be [remembered][remember] before the RCPaywallViewController is instantiated, so as to
 * "reserve" a spot in the Compose slot table.
 */
private class ViewControllerWrapper(var value: RCPaywallViewController?)
