package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewControllerDelegateProtocol
import io.shortway.kobankat.CustomerInfo
import objcnames.classes.RCCustomerInfo
import objcnames.classes.RCOffering
import platform.darwin.NSObject

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


private class PaywallViewControllerDelegate(
    private val onRestoreCompleted: (customerInfo: CustomerInfo) -> Unit
) : RCPaywallViewControllerDelegateProtocol,
    NSObject() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishRestoringWithCustomerInfo: RCCustomerInfo
    ) {
        super.paywallViewController(controller, didFinishRestoringWithCustomerInfo)
        onRestoreCompleted(didFinishRestoringWithCustomerInfo as CustomerInfo)
    }
}
