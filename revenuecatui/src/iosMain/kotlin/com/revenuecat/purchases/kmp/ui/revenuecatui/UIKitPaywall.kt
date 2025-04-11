package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallFooterViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import com.revenuecat.purchases.kmp.mappings.toIosOffering
import objcnames.classes.RCOffering

@Composable
internal fun UIKitPaywall(
    options: PaywallOptions,
    footer: Boolean,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    /**
     * Our intrinsic content size according to UIKit.
     */
    var intrinsicContentSizePx by remember { mutableStateOf(0) }

    // We remember this wrapper so we can keep a reference to RCPaywallViewController, even during
    // recompositions. RCPaywallViewController itself is not yet instantiated here.
    val viewControllerWrapper = remember { ViewControllerWrapper(null) }

    // Keeping references to avoid them being deallocated.
    val dismissRequestedHandler: (RCPaywallViewController?) -> Unit =
        remember(options.dismissRequest) { { options.dismissRequest() } }
    val delegate = remember(options.listener) {
        IosPaywallDelegate(options.listener) {
            // UIKit reports that our height was updated, so we're updating intrinsicContentSizePx
            // to force a new measurement phase (below).
            viewControllerWrapper.value?.view
                ?.getIntrinsicContentSizeOfFirstSubView()
                ?.also { intrinsicContentSizePx = with(density) { it.dp.roundToPx() } }
        }
    }

    UIKitViewController(
        modifier = modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(
                if (constraints.minHeight == 0 && constraints.maxHeight > 0)
                // We are being asked to wrap our own content height. We will use the measurement
                // done by UIKit.
                    constraints.copy(minHeight = intrinsicContentSizePx)
                else constraints
            )

            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        },
        factory = {
            val viewController = if (footer) RCPaywallFooterViewController(
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

            viewController
                .also {
                    // The first subview has an actual intrinsic content size. We keep a reference
                    // so we can use it in our measurement phase (above).
                    it.view.getIntrinsicContentSizeOfFirstSubView()
                        ?.also { intrinsicContentSizePx = with(density) { it.dp.roundToPx() } }
                }
                .apply { setDelegate(delegate) }
                .also { viewControllerWrapper.value = it }
        },
        properties = uiKitInteropPropertiesNonExperimental(
            nonCooperativeInteractionMode = true,
            isNativeAccessibilityEnabled = true,
        )
    )
}

/**
 * Can be [remembered][remember] before the RCPaywallViewController is instantiated, so as to
 * "reserve" a spot in the Compose slot table.
 */
private class ViewControllerWrapper(var value: RCPaywallViewController?)
