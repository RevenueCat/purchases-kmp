package com.revenuecat.maestro.e2e

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCSignatureOverride
import platform.CoreGraphics.CGSize
import platform.Foundation.NSNumber
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.darwin.NSObject
import swiftPMImport.com.revenuecat.purchases.kn.ui.PaywallProxy
import swiftPMImport.com.revenuecat.purchases.kn.ui.PaywallViewCreationParams
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewControllerDelegateWrapperProtocol

// Retained to prevent GC since PaywallProxy holds its delegate weakly.
private var retainedProxy: PaywallProxy? = null
private var retainedDelegate: ModalPaywallDelegate? = null

@Composable
actual fun PaywallPresenter(onDismiss: () -> Unit) {
    DisposableEffect(Unit) {
        presentPaywallModally(onDismiss)
        onDispose {
            retainedProxy = null
            retainedDelegate = null
        }
    }
}

private fun presentPaywallModally(onDismiss: () -> Unit) {
    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController ?: return
    var presentingVC = rootVC
    while (presentingVC.presentedViewController != null) {
        presentingVC = presentingVC.presentedViewController!!
    }

    val proxy = PaywallProxy()
    val delegate = ModalPaywallDelegate(onDismiss = {
        presentingVC.dismissViewControllerAnimated(true, completion = null)
        retainedProxy = null
        retainedDelegate = null
        onDismiss()
    })
    retainedProxy = proxy
    retainedDelegate = delegate
    proxy.setDelegate(delegate)

    val params = PaywallViewCreationParams().apply {
        setDisplayCloseButton(NSNumber(bool = true))
    }
    val paywallVC = proxy.createPaywallViewWithParams(params)
    val navController = UINavigationController(rootViewController = paywallVC)
    navController.modalPresentationStyle = 0 // fullScreen

    presentingVC.presentViewController(navController, animated = true, completion = null)
}

private class ModalPaywallDelegate(
    private val onDismiss: () -> Unit,
) : RCPaywallViewControllerDelegateWrapperProtocol, NSObject() {

    override fun paywallViewControllerRequestedDismissal(controller: RCPaywallViewController) {
        onDismiss()
    }

    override fun paywallViewControllerWasDismissed(controller: RCPaywallViewController) {}
    override fun paywallViewControllerDidStartPurchase(controller: RCPaywallViewController) {}
    override fun paywallViewControllerDidCancelPurchase(controller: RCPaywallViewController) {}

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailPurchasingWithErrorDictionary: Map<Any?, *>
    ) {}

    override fun paywallViewControllerDidStartRestore(controller: RCPaywallViewController) {}

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailRestoringWithErrorDictionary: Map<Any?, *>
    ) {}

    override fun paywallViewController(
        controller: RCPaywallViewController,
        didChangeSizeTo: CValue<CGSize>
    ) {}
}
