package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.mappings.toIosOffering
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.layoutViewController
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.rememberLayoutViewControllerState
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreGraphics.CGSize
import platform.darwin.NSObject
import swiftPMImport.com.revenuecat.purchases.kn.ui.HybridPurchaseLogicBridge
import swiftPMImport.com.revenuecat.purchases.kn.ui.PaywallProxy
import swiftPMImport.com.revenuecat.purchases.kn.ui.PaywallViewCreationParams
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

    if (options.purchaseLogic != null && footer) {
        println(
            "[RevenueCat] Warning: purchaseLogic is not supported with PaywallFooter on iOS. " +
                    "The custom purchase logic will be ignored."
        )
    }

    // PaywallProxy is used for all non-footer paywalls (with or without custom purchase logic).
    // It creates the VC and sets itself as its delegate (weak ref), so retain it here.
    val coroutineScope = rememberCoroutineScope()
    val proxy = remember { if (!footer) PaywallProxy() else null }
    val purchaseLogicBridge = remember(options.purchaseLogic, options.offering) {
        if (!footer) {
            options.purchaseLogic?.toHybridPurchaseLogicBridge(
                packages = options.offering?.availablePackages.orEmpty(),
                scope = coroutineScope,
            )
        } else null
    }
    val proxyDelegate = remember(options.listener, options.dismissRequest) {
        if (!footer) {
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

    // Footer path still uses direct VC constructors since PaywallProxy doesn't support
    // PaywallViewCreationParams for footer views.
    val dismissRequestedHandler: (RCPaywallViewController?) -> Unit =
        remember(options.dismissRequest) { { options.dismissRequest() } }
    val footerDelegate = remember(options.listener) {
        if (footer) {
            IosPaywallDelegate(
                listener = options.listener,
                onHeightChange = { layoutViewControllerState.updateIntrinsicContentSize() }
            )
        } else null
    }

    UIKitViewController(
        modifier = modifier.layoutViewController(layoutViewControllerState),
        factory = {
            if (!footer && proxy != null) {
                val params = PaywallViewCreationParams().apply {
                    purchaseLogicBridge?.let { setPurchaseLogicBridge(it) }
                    setDisplayCloseButton(
                        platform.Foundation.NSNumber(bool = options.shouldDisplayDismissButton)
                    )
                    if (options.customVariables.isNotEmpty()) {
                        setCustomVariables(options.customVariables.toIosCustomVariables())
                    }
                }
                val offering = options.offering?.toIosOffering()
                if (offering != null) {
                    params.setOfferingIdentifier(offering.identifier())
                }
                proxy.createPaywallViewWithParams(params)
                    .also { layoutViewControllerState.setViewController(it) }
            } else {
                RCPaywallFooterViewController(
                    offering = options.offering?.toIosOffering() as? RCOffering,
                    displayCloseButton = options.shouldDisplayDismissButton,
                    shouldBlockTouchEvents = false,
                    dismissRequestedHandler = dismissRequestedHandler,
                ).apply { setDelegate(footerDelegate) }
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
    packages: List<Package>,
    scope: CoroutineScope,
): HybridPurchaseLogicBridge {
    val purchaseLogic = this
    val packagesByIdentifier = packages.associateBy { it.identifier }

    return HybridPurchaseLogicBridge(
        onPerformPurchase = { eventData ->
            eventData.launchBridgeRequest(scope) { requestId ->
                @Suppress("UNCHECKED_CAST")
                val packageDict =
                    eventData?.get(HybridPurchaseLogicBridge.eventKeyPackageBeingPurchased())
                            as? Map<Any?, *>
                val packageIdentifier = packageDict?.get("identifier") as? String
                val rcPackage = packageIdentifier?.let { packagesByIdentifier[it] }
                    ?: error("Unable to find package with identifier: $packageIdentifier")
                val params = PaywallPurchaseLogicParams(
                    rcPackage = rcPackage,
                )
                purchaseLogic.performPurchase(params)
            }
        },
        onPerformRestore = { eventData ->
            eventData.launchBridgeRequest(scope) {
                val customerInfo = fetchCustomerInfo()
                purchaseLogic.performRestore(customerInfo)
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
            errorMessage = result.errorDetails?.let {
                it.underlyingErrorMessage ?: it.code.description
            },
        )
    }
}

private suspend fun fetchCustomerInfo(): CustomerInfo =
    suspendCancellableCoroutine { continuation ->
        Purchases.sharedInstance.getCustomerInfo(
            onError = { error ->
                continuation.resumeWith(Result.failure(Exception(error.message)))
            },
            onSuccess = { customerInfo ->
                continuation.resumeWith(Result.success(customerInfo))
            },
        )
    }

private fun Map<Any?, *>.toPurchasesError(): PurchasesError = PurchasesError(
    code = PurchasesErrorCode.UnknownError,
    underlyingErrorMessage = this["message"] as? String,
)

private fun CValue<CGSize>.heightInt(): Int =
    memScoped { ptr.pointed.height.toInt() }

private fun Map<String, CustomVariableValue>.toIosCustomVariables(): Map<Any?, Any> =
    entries.associate { (key, value) ->
        key to when (value) {
            is CustomVariableValue.String -> value.value as Any
            is CustomVariableValue.Number -> value.value as Any
            is CustomVariableValue.Boolean -> value.value as Any
            else -> error("Unknown CustomVariableValue type: ${value::class.simpleName}")
        }
    }
