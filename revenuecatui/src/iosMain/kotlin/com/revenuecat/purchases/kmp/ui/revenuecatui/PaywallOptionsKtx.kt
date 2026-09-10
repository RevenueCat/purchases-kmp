package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toPackage
import com.revenuecat.purchases.kmp.mappings.toPurchasesErrorOrThrow
import com.revenuecat.purchases.kmp.mappings.toStoreTransaction
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import platform.CoreGraphics.CGSize
import platform.Foundation.NSError
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.darwin.NSObject
import com.revenuecat.purchases.kn.core.RCCustomerInfo
import com.revenuecat.purchases.kn.core.RCPackage
import com.revenuecat.purchases.kn.core.RCStoreTransaction
import com.revenuecat.purchases.kn.ui.RCPaywallViewController
import com.revenuecat.purchases.kn.ui.RCPaywallViewControllerDelegateProtocol
import com.revenuecat.purchases.kn.ui.RCPaywallInteractionEvent
import com.revenuecat.purchases.kn.ui.RCCustomerInfo as RCCustomerInfoFromKnUi
import com.revenuecat.purchases.kn.ui.RCPackage as RCPackageFromKnUi
import com.revenuecat.purchases.kn.ui.RCStoreTransaction as RCStoreTransactionFromKnUi

internal class IosPaywallDelegate(
    private val listener: PaywallListener?,
    private val onHeightChange: (Int) -> Unit
) : RCPaywallViewControllerDelegateProtocol,
    NSObject() {

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didStartPurchaseWithPackage: RCPackageFromKnUi,
    ) {
        listener?.onPurchaseStarted(
            (didStartPurchaseWithPackage as RCPackage).toPackage()
        )
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFinishPurchasingWithCustomerInfo: RCCustomerInfoFromKnUi,
        transaction: RCStoreTransactionFromKnUi?,
    ) {
        listener?.onPurchaseCompleted(
            (didFinishPurchasingWithCustomerInfo as RCCustomerInfo).toCustomerInfo(),
            (transaction as RCStoreTransaction).toStoreTransaction()
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
        didFinishRestoringWithCustomerInfo: RCCustomerInfoFromKnUi
    ) {
        listener?.onRestoreCompleted(
            (didFinishRestoringWithCustomerInfo as RCCustomerInfo).toCustomerInfo()
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

    override fun paywallViewControllerDidOpenWebCheckout(controller: RCPaywallViewController) {
        listener?.onWebCheckoutOpened()
    }

    override fun paywallViewController(
        controller: RCPaywallViewController,
        didOpenURL: NSURL
    ) {
        listener?.onUrlOpened(didOpenURL.absoluteString ?: "")
    }

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didTrackInteraction: RCPaywallInteractionEvent
    ) {
        listener?.onInteraction(PaywallInteractionEvent(didTrackInteraction.rawProperties().toKotlinValues()))
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

private fun Map<Any?, *>.toKotlinValues(): Map<String, Any> =
    entries.associate { (key, value) ->
        key as String to if (value is NSNumber) value.toKotlinValue() else value as Any
    }

// Swift Bool crosses to Kotlin as an NSNumber whose objCType is "c"; the interaction contract has no
// floating-point keys, so every other NSNumber is an integer.
private fun NSNumber.toKotlinValue(): Any =
    if (objCType?.toKString() == "c") boolValue else longLongValue
