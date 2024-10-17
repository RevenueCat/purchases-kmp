package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallFooterViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import com.revenuecat.purchases.kmp.mappings.toIosOffering
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import objcnames.classes.RCOffering
import platform.UIKit.UIView

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
    val delegate = remember(options.listener) { IosPaywallDelegate(options.listener) }
    // We remember this wrapper so we can keep a reference to RCPaywallViewController, even during
    // recompositions. RCPaywallViewController itself is not yet instantiated here.
    val viewControllerWrapper = remember { ViewControllerWrapper(null) }
    UIKitViewController(
        modifier = modifier,
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
                    // The first subview has an actual intrinsic content size. We immediately let
                    // the parent know, so it can set our height accordingly. This should happen
                    // before the first frame is drawn.
                    if (footer) (it.view.subviews.firstOrNull() as? UIView)
                        ?.getIntrinsicContentSize()
                        ?.also(onHeightChange)
                }
                .apply { setDelegate(delegate) }
                .also { viewControllerWrapper.value = it }
        },
    )
}

private fun UIView.getIntrinsicContentSize(): Int {
    var size: Int? = null
    memScoped { size = intrinsicContentSize.ptr.pointed.height.toInt() }
    return size!!
}

/**
 * Can be [remembered][remember] before the RCPaywallViewController is instantiated, so as to
 * "reserve" a spot in the Compose slot table.
 */
private class ViewControllerWrapper(var value: RCPaywallViewController?)
