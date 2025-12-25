package com.revenuecat.purchases.kmp.ui.revenuecatui

import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewControllerDelegateProtocol
import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toPackage
import com.revenuecat.purchases.kmp.mappings.toPurchasesErrorOrThrow
import com.revenuecat.purchases.kmp.mappings.toStoreTransaction
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.CoreGraphics.CGSize
import platform.Foundation.NSError
import platform.darwin.NSObject
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCustomerInfo
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPackage
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreTransaction 

internal class IosPaywallDelegate(
    private val listener: PaywallListener?,
    private val onHeightChange: (Int) -> Unit
) : RCPaywallViewControllerDelegateProtocol,
    NSObject() {

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didStartPurchaseWithPackage: RCPackage
    ) {
        listener?.onPurchaseStarted(
            didStartPurchaseWithPackage.toPackage()
        )
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishPurchasingWithCustomerInfo: RCCustomerInfo,
        transaction: RCStoreTransaction?
    ) {
        listener?.onPurchaseCompleted(
            (didFinishPurchasingWithCustomerInfo).toCustomerInfo(),
            transaction?.toStoreTransaction()!! // FIXME
        )
    }

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailPurchasingWithError: NSError
    ) {
        listener?.onPurchaseError(didFailPurchasingWithError.toPurchasesErrorOrThrow())
    }

    override fun paywallViewControllerDidCancelPurchase(controller: RCPaywallViewController) {
        listener?.onPurchaseCancelled()
    }

    override fun paywallViewControllerDidStartRestore(controller: RCPaywallViewController) {
        listener?.onRestoreStarted()
    }

    @Suppress("CAST_NEVER_SUCCEEDS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishRestoringWithCustomerInfo: RCCustomerInfo
    ) {
        listener?.onRestoreCompleted(
            (didFinishRestoringWithCustomerInfo).toCustomerInfo()
        )
    }

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailRestoringWithError: NSError
    ) {
        listener?.onRestoreError(didFailRestoringWithError.toPurchasesErrorOrThrow())
    }

    override fun paywallViewController(
        controller: RCPaywallViewController,
        didChangeSizeTo: CValue<CGSize>
    ) {
        var height: Int? = null
        memScoped { height = didChangeSizeTo.ptr.pointed.height.toInt() }
        onHeightChange(height!!)
    }
}
