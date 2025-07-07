package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import cocoapods.PurchasesHybridCommonUI.CustomerCenterUIViewController
import cocoapods.PurchasesHybridCommonUI.RCCustomerCenterViewControllerDelegateWrapperProtocol
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.layoutViewController
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.rememberLayoutViewControllerState
import platform.darwin.NSObject

@Composable
internal fun UIKitCustomerCenter(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val density = LocalDensity.current

    // Intrinsic content size from UIKit
    var intrinsicContentSizePx by remember { mutableStateOf(0) }

    // We remember this wrapper so we can keep a reference to CustomerCenterUIViewController, even
    // during recompositions. CustomerCenterUIViewController itself is not yet instantiated here.
    val viewControllerWrapper = remember { ViewControllerWrapper(null) }
    val layoutViewControllerState = rememberLayoutViewControllerState(
        viewController = { viewControllerWrapper.wrapped },
        intrinsicContentSizePx = { intrinsicContentSizePx },
    )

    // Keep a reference to IosCustomerCenterDelegate across recompositions
    val delegate = remember { IosCustomerCenterDelegate(onDismiss) }

    UIKitViewController(
        modifier = modifier.layoutViewController(layoutViewControllerState),
        factory = {
            CustomerCenterUIViewController()
                .apply {
                    setDelegate(delegate)
                    setOnCloseHandler(onDismiss)
                    view.getIntrinsicContentSizeOfFirstSubView()
                        ?.also { intrinsicContentSizePx = with(density) { it.dp.roundToPx() } }
                }.also {
                    viewControllerWrapper.wrapped = it
                }
        },
        properties = uiKitInteropPropertiesNonExperimental(
            nonCooperativeInteractionMode = true,
            isNativeAccessibilityEnabled = true,
        )
    )
}

internal class IosCustomerCenterDelegate(
    private val onDismiss: () -> Unit
) : RCCustomerCenterViewControllerDelegateWrapperProtocol, NSObject() {

    override fun customerCenterViewControllerWasDismissed(controller: CustomerCenterUIViewController) {
        onDismiss()
    }
}
