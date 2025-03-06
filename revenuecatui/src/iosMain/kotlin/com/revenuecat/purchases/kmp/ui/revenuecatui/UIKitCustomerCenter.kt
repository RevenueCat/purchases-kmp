package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import cocoapods.PurchasesHybridCommonUI.CustomerCenterUIViewController
import cocoapods.PurchasesHybridCommonUI.RCCustomerCenterViewControllerDelegateWrapperProtocol
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.UIKit.UIView
import platform.darwin.NSObject

@Composable
internal fun UIKitCustomerCenter(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val density = LocalDensity.current

    // Intrinsic content size from UIKit
    var intrinsicContentSizePx by remember { mutableStateOf(0) }

    // Keep a reference to the CustomerCenterUIViewController and IosCustomerCenterDelegate across recompositions
    val viewControllerWrapper = remember { CustomerCenterUIViewControllerWrapper(null) }
    val delegate = remember { IosCustomerCenterDelegate(onDismiss) }

    UIKitViewController(
        modifier = modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(
                if (constraints.minHeight == 0 && constraints.maxHeight > 0)
                    constraints.copy(minHeight = intrinsicContentSizePx)
                else constraints
            )

            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        },
        factory = {
            val viewController = CustomerCenterUIViewController()

            viewController
                .also {
                    it.view.getIntrinsicContentSizeOfFirstSubView()
                        ?.also { intrinsicContentSizePx = with(density) { it.dp.roundToPx() } }
                }
                .also { it.setDelegate(delegate) }
                .also { it.setOnCloseHandler({
                    onDismiss()
                }) }
                .also { viewControllerWrapper.value = it }
        },
    )
}

// Helper to get intrinsic height from UIKit view
private fun UIView.getIntrinsicContentSize(): Int {
    var size: Int? = null
    memScoped { size = intrinsicContentSize.ptr.pointed.height.toInt() }
    return size!!
}

private fun UIView.getIntrinsicContentSizeOfFirstSubView(): Int? =
    (subviews.firstOrNull() as? UIView)?.getIntrinsicContentSize()

// Wrapper class for CustomerCenterUIViewController
private class CustomerCenterUIViewControllerWrapper(var value: CustomerCenterUIViewController?)

internal class IosCustomerCenterDelegate(
    private val onDismiss: () -> Unit
) : RCCustomerCenterViewControllerDelegateWrapperProtocol, NSObject() {

    override fun customerCenterViewControllerWasDismissed(controller: CustomerCenterUIViewController) {
        onDismiss()
    }
}
