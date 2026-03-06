package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCCustomerCenterViewControllerDelegateProtocol
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCCustomerCenterViewController
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.layoutViewController
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.rememberLayoutViewControllerState
import platform.darwin.NSObject

@Composable
internal fun UIKitCustomerCenter(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val layoutViewControllerState = rememberLayoutViewControllerState()

    // Keep a reference to IosCustomerCenterDelegate across recompositions
    val delegate = remember { IosCustomerCenterDelegate(onDismiss) }

    UIKitViewController(
        modifier = modifier.layoutViewController(layoutViewControllerState),
        factory = {
            RCCustomerCenterViewController(delegate = delegate)
                .also {
                    layoutViewControllerState.setViewController(it)
                }
        },
        properties = uiKitInteropPropertiesNonExperimental(
            nonCooperativeInteractionMode = true,
            isNativeAccessibilityEnabled = true,
        )
    )
}

private class IosCustomerCenterDelegate(
    private val onDismiss: () -> Unit
) : RCCustomerCenterViewControllerDelegateProtocol, NSObject() {

    override fun customerCenterViewControllerWasDismissed(controller: RCCustomerCenterViewController) {
        onDismiss()
    }
}
