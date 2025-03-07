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

    // Keep a reference to IosCustomerCenterDelegate across recompositions
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
            CustomerCenterUIViewController()
                .apply {
                setDelegate(delegate)
                setOnCloseHandler(onDismiss)
                view.getIntrinsicContentSizeOfFirstSubView()
                    ?.also { intrinsicContentSizePx = with(density) { it.dp.roundToPx() } }
            }
        },
    )
}

internal class IosCustomerCenterDelegate(
    private val onDismiss: () -> Unit
) : RCCustomerCenterViewControllerDelegateWrapperProtocol, NSObject() {

    override fun customerCenterViewControllerWasDismissed(controller: CustomerCenterUIViewController) {
        onDismiss()
    }
}
