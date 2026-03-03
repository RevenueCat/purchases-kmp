package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.revenuecat.purchases.kmp.mappings.toIosOffering
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.layoutViewController
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.rememberLayoutViewControllerState
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import platform.CoreGraphics.CGSize
import platform.darwin.NSObject
import swiftPMImport.com.revenuecat.purchases.kn.ui.HybridPurchaseLogicBridge
import swiftPMImport.com.revenuecat.purchases.kn.ui.PaywallProxy
import swiftPMImport.com.revenuecat.purchases.kn.ui.PaywallViewCreationParams
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCOffering
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallFooterViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewControllerDelegateWrapperProtocol

@Composable
internal fun UIKitPaywall(
    options: PaywallOptions,
    footer: Boolean,
    modifier: Modifier = Modifier,
) {
    val layoutViewControllerState = rememberLayoutViewControllerState()
    val usePurchaseLogicProxy = options.purchaseLogic != null && !footer

    if (options.purchaseLogic != null && footer) {
        println("[RevenueCat] Warning: purchaseLogic is not supported with PaywallFooter on iOS. " +
                "The custom purchase logic will be ignored.")
    }

    // Keeping references to avoid them being deallocated.
    val dismissRequestedHandler: (RCPaywallViewController?) -> Unit =
        remember(options.dismissRequest) { { options.dismissRequest() } }
    val delegate = remember(options.listener) {
        IosPaywallDelegate(
            listener = options.listener,
            onHeightChange = { layoutViewControllerState.updateIntrinsicContentSize() }
        )
    }

    // For purchase logic path: use PaywallProxy + HybridPurchaseLogicBridge.
    // The proxy must be retained because it sets itself as the VC's delegate (weak ref).
    val coroutineScope = rememberCoroutineScope()
    val proxy = remember(options.purchaseLogic) {
        if (usePurchaseLogicProxy) PaywallProxy() else null
    }
    val purchaseLogicBridge = remember(options.purchaseLogic, options.offering) {
        if (usePurchaseLogicProxy) {
            options.purchaseLogic?.toHybridPurchaseLogicBridge(
                packages = options.offering?.availablePackages.orEmpty(),
                scope = coroutineScope,
            )
        } else null
    }
    // Set a delegate on the proxy to handle dismiss and listener events.
    val proxyDelegate = remember(options.listener, options.dismissRequest) {
        if (usePurchaseLogicProxy) {
            IosPaywallProxyDelegate(
                listener = options.listener,
                dismissRequest = options.dismissRequest,
                onHeightChange = { layoutViewControllerState.updateIntrinsicContentSize() },
            )
        } else null
    }
    proxy?.setDelegate(proxyDelegate)

    // Clean up bridge pending requests when the composable leaves composition.
    DisposableEffect(purchaseLogicBridge) {
        onDispose {
            purchaseLogicBridge?.cancelPending()
        }
    }

    UIKitViewController(
        modifier = modifier.layoutViewController(layoutViewControllerState),
        factory = {
            if (usePurchaseLogicProxy && proxy != null && purchaseLogicBridge != null) {
                // Use PaywallProxy path: proxy creates VC with performPurchase/performRestore
                // and sets itself as the VC's delegate. Do NOT overwrite the delegate.
                val params = PaywallViewCreationParams().apply {
                    setPurchaseLogicBridge(purchaseLogicBridge)
                }
                val offering = options.offering?.toIosOffering()
                if (offering != null) {
                    params.setOfferingIdentifier(offering.identifier())
                }
                proxy.createPaywallViewWithParams(params)
                    .also { layoutViewControllerState.setViewController(it) }
            } else {
                // Standard path: no purchase logic.
                val paywallViewController = if (footer) {
                    RCPaywallFooterViewController(
                        offering = options.offering?.toIosOffering() as? RCOffering,
                        displayCloseButton = options.shouldDisplayDismissButton,
                        shouldBlockTouchEvents = false,
                        dismissRequestedHandler = dismissRequestedHandler,
                    )
                } else {
                    RCPaywallViewController(
                        offering = options.offering?.toIosOffering() as? RCOffering,
                        displayCloseButton = options.shouldDisplayDismissButton,
                        shouldBlockTouchEvents = false,
                        dismissRequestedHandler = dismissRequestedHandler,
                    )
                }
                paywallViewController
                    .apply { setDelegate(delegate) }
                    .also { layoutViewControllerState.setViewController(it) }
            }
        },
        properties = uiKitInteropPropertiesNonExperimental(
            nonCooperativeInteractionMode = true,
            isNativeAccessibilityEnabled = true,
        )
    )
}

/**
 * Delegate for the [PaywallProxy] path. Implements the dictionary-based
 * [RCPaywallViewControllerDelegateWrapperProtocol] to receive events from the proxy.
 * Handles dismiss requests, height changes, and forwards simple listener events.
 */
internal class IosPaywallProxyDelegate(
    private val listener: PaywallListener?,
    private val dismissRequest: () -> Unit,
    private val onHeightChange: (Int) -> Unit,
) : RCPaywallViewControllerDelegateWrapperProtocol,
    NSObject() {

    override fun paywallViewControllerRequestedDismissal(
        controller: RCPaywallViewController
    ) {
        dismissRequest()
    }

    override fun paywallViewControllerWasDismissed(controller: RCPaywallViewController) {
        // Cleanup is handled by PaywallProxy internally.
    }

    override fun paywallViewControllerDidStartPurchase(controller: RCPaywallViewController) {
        // onPurchaseStarted requires a Package object that we don't have in the proxy path.
    }

    override fun paywallViewControllerDidCancelPurchase(controller: RCPaywallViewController) {
        listener?.onPurchaseCancelled()
    }

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailPurchasingWithErrorDictionary: Map<Any?, *>
    ) {
        listener?.onPurchaseError(didFailPurchasingWithErrorDictionary.toPurchasesError())
    }

    override fun paywallViewControllerDidStartRestore(controller: RCPaywallViewController) {
        listener?.onRestoreStarted()
    }

    @ObjCSignatureOverride
    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun paywallViewController(
        controller: RCPaywallViewController,
        didFailRestoringWithErrorDictionary: Map<Any?, *>
    ) {
        listener?.onRestoreError(didFailRestoringWithErrorDictionary.toPurchasesError())
    }

    override fun paywallViewController(
        controller: RCPaywallViewController,
        didChangeSizeTo: CValue<CGSize>
    ) {
        onHeightChange(didChangeSizeTo.heightInt())
    }
}

private fun PaywallPurchaseLogic.toHybridPurchaseLogicBridge(
    packages: List<com.revenuecat.purchases.kmp.models.Package>,
    scope: CoroutineScope,
): HybridPurchaseLogicBridge {
    val purchaseLogic = this
    val packagesByIdentifier = packages.associateBy { it.identifier }

    return HybridPurchaseLogicBridge(
        onPerformPurchase = { eventData ->
            eventData.launchBridgeRequest(scope) { requestId ->
                @Suppress("UNCHECKED_CAST")
                val packageDict = eventData?.get(HybridPurchaseLogicBridge.eventKeyPackageBeingPurchased())
                    as? Map<Any?, *>
                val packageIdentifier = packageDict?.get("identifier") as? String
                val rcPackage = packageIdentifier?.let { packagesByIdentifier[it] }
                    ?: error("Unable to find package with identifier: $packageIdentifier")
                purchaseLogic.performPurchase(rcPackage)
            }
        },
        onPerformRestore = { eventData ->
            eventData.launchBridgeRequest(scope) {
                purchaseLogic.performRestore()
            }
        }
    )
}

/**
 * Extracts the request ID from bridge event data, launches a coroutine to execute [block],
 * and resolves the result (or error) back to the bridge.
 */
private fun Map<Any?, *>?.launchBridgeRequest(
    scope: CoroutineScope,
    block: suspend (requestId: String) -> PurchaseLogicResult,
) {
    val requestId = this?.get(HybridPurchaseLogicBridge.eventKeyRequestId()) as? String ?: return
    scope.launch {
        try {
            resolveResult(requestId, block(requestId))
        } catch (e: Exception) {
            HybridPurchaseLogicBridge.resolveResultWithRequestId(
                requestId = requestId,
                resultString = HybridPurchaseLogicBridge.resultError(),
                errorMessage = e.message,
            )
        }
    }
}

private fun resolveResult(requestId: String, result: PurchaseLogicResult) {
    when (result) {
        is PurchaseLogicResult.Success -> HybridPurchaseLogicBridge.resolveResultWithRequestId(
            requestId = requestId,
            resultString = HybridPurchaseLogicBridge.resultSuccess(),
        )
        is PurchaseLogicResult.Cancellation -> HybridPurchaseLogicBridge.resolveResultWithRequestId(
            requestId = requestId,
            resultString = HybridPurchaseLogicBridge.resultCancellation(),
        )
        is PurchaseLogicResult.Error -> HybridPurchaseLogicBridge.resolveResultWithRequestId(
            requestId = requestId,
            resultString = HybridPurchaseLogicBridge.resultError(),
            errorMessage = result.errorMessage,
        )
    }
}

private fun Map<Any?, *>.toPurchasesError(): PurchasesError = PurchasesError(
    code = PurchasesErrorCode.UnknownError,
    underlyingErrorMessage = this["message"] as? String,
)

private fun CValue<CGSize>.heightInt(): Int =
    memScoped { ptr.pointed.height.toInt() }
