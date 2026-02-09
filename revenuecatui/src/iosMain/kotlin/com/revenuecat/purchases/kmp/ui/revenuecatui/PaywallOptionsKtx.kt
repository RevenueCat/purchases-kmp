package com.revenuecat.purchases.kmp.ui.revenuecatui

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
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCCustomerInfo
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPackage
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewControllerDelegateProtocol
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCStoreTransaction
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCustomerInfo as PhcCustomerInfo
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPackage as PhcPackage
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStoreTransaction as PhcStoreTransaction

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
            (didStartPurchaseWithPackage as PhcPackage).toPackage()
        )
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishPurchasingWithCustomerInfo: RCCustomerInfo,
        transaction: RCStoreTransaction?
    ) {
        listener?.onPurchaseCompleted(
            (didFinishPurchasingWithCustomerInfo as PhcCustomerInfo).toCustomerInfo(),
            (transaction as PhcStoreTransaction).toStoreTransaction()
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

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishRestoringWithCustomerInfo: RCCustomerInfo
    ) {
        listener?.onRestoreCompleted(
            (didFinishRestoringWithCustomerInfo as PhcCustomerInfo).toCustomerInfo()
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
