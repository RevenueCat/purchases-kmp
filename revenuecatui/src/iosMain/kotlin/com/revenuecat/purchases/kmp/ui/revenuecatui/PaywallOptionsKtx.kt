package com.revenuecat.purchases.kmp.ui.revenuecatui

// Not sure why we have 2 Kotlin mappings to the same ObjC classes.
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewController
import cocoapods.PurchasesHybridCommonUI.RCPaywallViewControllerDelegateProtocol
import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toPackage
import com.revenuecat.purchases.kmp.mappings.toPurchasesErrorOrThrow
import com.revenuecat.purchases.kmp.mappings.toStoreTransaction
import kotlinx.cinterop.CValue
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.CoreGraphics.CGSize
import platform.Foundation.NSError
import platform.darwin.NSObject
import cocoapods.PurchasesHybridCommon.RCCustomerInfo as PhcCustomerInfo
import cocoapods.PurchasesHybridCommon.RCPackage as PhcPackage
import cocoapods.PurchasesHybridCommon.RCStoreTransaction as PhcStoreTransaction
import objcnames.classes.RCCustomerInfo as ObjcNamesCustomerInfo
import objcnames.classes.RCPackage as ObjcNamesPackage
import objcnames.classes.RCStoreTransaction as ObjcNamesStoreTransaction

internal class IosPaywallDelegate(
    private val listener: PaywallListener?,
    private val onHeightChange: (Int) -> Unit
) : RCPaywallViewControllerDelegateProtocol,
    NSObject() {

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didStartPurchaseWithPackage: ObjcNamesPackage
    ) {
        listener?.onPurchaseStarted(
            (didStartPurchaseWithPackage as PhcPackage).toPackage()
        )
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishPurchasingWithCustomerInfo: ObjcNamesCustomerInfo,
        transaction: ObjcNamesStoreTransaction?
    ) {
        listener?.onPurchaseCompleted(
            (didFinishPurchasingWithCustomerInfo as PhcCustomerInfo).toCustomerInfo(),
            (transaction as PhcStoreTransaction).toStoreTransaction()
        )
    }

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
        didFinishRestoringWithCustomerInfo: ObjcNamesCustomerInfo
    ) {
        listener?.onRestoreCompleted(
            (didFinishRestoringWithCustomerInfo as PhcCustomerInfo).toCustomerInfo()
        )
    }

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
