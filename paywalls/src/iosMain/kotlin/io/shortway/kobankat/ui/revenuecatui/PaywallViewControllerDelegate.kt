package io.shortway.kobankat.ui.revenuecatui

import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewControllerDelegateProtocol
import io.shortway.kobankat.CustomerInfo
import objcnames.classes.RCCustomerInfo
import platform.darwin.NSObject

internal class PaywallViewControllerDelegate(
    private val onRestoreCompleted: (customerInfo: CustomerInfo) -> Unit
) : RCPaywallViewControllerDelegateProtocol,
    NSObject() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishRestoringWithCustomerInfo: RCCustomerInfo
    ) {
        onRestoreCompleted(didFinishRestoringWithCustomerInfo as CustomerInfo)
    }
}
