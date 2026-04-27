package com.revenuecat.maestro.e2e

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCSignatureOverride
import platform.CoreGraphics.CGSize
import platform.Foundation.NSError
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.darwin.NSObject
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewControllerDelegateProtocol
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCCustomerInfo as RCCustomerInfoFromKnUi
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPackage as RCPackageFromKnUi
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCStoreTransaction as RCStoreTransactionFromKnUi

private var retainedDelegate: ModalPaywallDelegate? = null

@Composable
actual fun PaywallPresenter(onDismiss: () -> Unit) {
    DisposableEffect(Unit) {
        presentPaywallModally(onDismiss)
        onDispose {
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

    val delegate = ModalPaywallDelegate(onDismiss = {
        presentingVC.dismissViewControllerAnimated(true, completion = null)
        retainedDelegate = null
        onDismiss()
    })
    retainedDelegate = delegate

    val paywallVC = RCPaywallViewController(
        offering = null,
        displayCloseButton = true,
        shouldBlockTouchEvents = false,
        dismissRequestedHandler = { _ -> delegate.dismiss() },
        purchaseHandler = null,
    )
    paywallVC.setDelegate(delegate)

    val navController = UINavigationController(rootViewController = paywallVC)
    navController.modalPresentationStyle = 0 // fullScreen

    presentingVC.presentViewController(navController, animated = true, completion = null)
}

private class ModalPaywallDelegate(
    private val onDismiss: () -> Unit,
) : RCPaywallViewControllerDelegateProtocol, NSObject() {

    fun dismiss() {
        onDismiss()
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didStartPurchaseWithPackage: RCPackageFromKnUi,
    ) {}

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishPurchasingWithCustomerInfo: RCCustomerInfoFromKnUi,
        transaction: RCStoreTransactionFromKnUi?,
    ) {}

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailPurchasingWithError: NSError,
    ) {}

    override fun paywallViewControllerDidCancelPurchase(controller: RCPaywallViewController) {}
    override fun paywallViewControllerDidStartRestore(controller: RCPaywallViewController) {}

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishRestoringWithCustomerInfo: RCCustomerInfoFromKnUi,
    ) {}

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailRestoringWithError: NSError,
    ) {}

    override fun paywallViewController(
        controller: RCPaywallViewController,
        didChangeSizeTo: CValue<CGSize>,
    ) {}
}
