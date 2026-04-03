package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ktx.awaitCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toIosOffering
import com.revenuecat.purchases.kmp.mappings.toPackage
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.layoutViewController
import com.revenuecat.purchases.kmp.ui.revenuecatui.modifier.rememberLayoutViewControllerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import platform.Foundation.NSError
import platform.Foundation.NSLocalizedDescriptionKey
import platform.darwin.NSObject
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPackage
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallFooterViewController
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallPurchaseHandlerProtocol
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPaywallViewController
import kotlin.coroutines.cancellation.CancellationException
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCOffering as RCOfferingFromKnUi
import swiftPMImport.com.revenuecat.purchases.kn.ui.RCPackage as RCPackageFromKnUi

@Composable
internal fun UIKitPaywall(
    options: PaywallOptions,
    footer: Boolean,
    modifier: Modifier = Modifier,
) {
    val layoutViewControllerState = rememberLayoutViewControllerState()
    val coroutineScope = rememberCoroutineScope()

    // Keeping references to avoid them being deallocated.
    val dismissRequestedHandler: (RCPaywallViewController?) -> Unit =
        remember(options.dismissRequest) { { options.dismissRequest() } }
    val delegate = remember(options.listener) {
        IosPaywallDelegate(
            listener = options.listener,
            onHeightChange = { layoutViewControllerState.updateIntrinsicContentSize() }
        )
    }

    UIKitViewController(
        modifier = modifier.layoutViewController(layoutViewControllerState),
        factory = {
            val paywallViewController = if (footer) RCPaywallFooterViewController(
                offering = options.offering?.toIosOffering() as? RCOfferingFromKnUi,
                displayCloseButton = options.shouldDisplayDismissButton,
                shouldBlockTouchEvents = false,
                dismissRequestedHandler = dismissRequestedHandler,
                purchaseHandler = options.purchaseLogic?.toPurchaseHandler(coroutineScope),
            ) else RCPaywallViewController(
                offering = options.offering?.toIosOffering() as? RCOfferingFromKnUi,
                displayCloseButton = options.shouldDisplayDismissButton,
                shouldBlockTouchEvents = false,
                dismissRequestedHandler = dismissRequestedHandler,
                purchaseHandler = options.purchaseLogic?.toPurchaseHandler(coroutineScope),
            )

            paywallViewController
                .apply { setDelegate(delegate) }
                .apply { setCustomVariables(options.customVariables) }
                .also { layoutViewControllerState.setViewController(it) }
        },
        properties = uiKitInteropPropertiesNonExperimental(
            nonCooperativeInteractionMode = true,
            isNativeAccessibilityEnabled = true,
        )
    )
}

private fun RCPaywallViewController.setCustomVariables(variables: Map<String, CustomVariableValue>) {
    variables.forEach { (key, value) ->
        when (value) {
            is CustomVariableValue.String -> setCustomVariable(value = value.value, forKey = key)
            is CustomVariableValue.Number -> setCustomVariableNumber(
                value = value.value,
                forKey = key
            )
            is CustomVariableValue.Boolean -> setCustomVariableBool(
                value = value.value,
                forKey = key
            )
            else -> error("Unknown CustomVariableValue type: ${value::class.simpleName}")
        }
    }
}

private fun PaywallPurchaseLogic.toPurchaseHandler(
    coroutineScope: CoroutineScope,
) = PurchaseHandler(this, coroutineScope) {
    Purchases.sharedInstance.awaitCustomerInfo()
}

private class PurchaseHandler(
    private val purchaseLogic: PaywallPurchaseLogic,
    private val coroutineScope: CoroutineScope,
    private val getCustomerInfo: suspend () -> CustomerInfo,
) : RCPaywallPurchaseHandlerProtocol, NSObject() {

    override fun performRestoreWithCompletion(completion: (success: Boolean, error: NSError?) -> Unit) {
        coroutineScope.launch {
            var success = false
            var error: NSError? = null
            try {
                val result = purchaseLogic.performRestore(getCustomerInfo())
                error = (result as? PurchaseLogicResult.Error)?.errorDetails?.toNSError()
                success = result is PurchaseLogicResult.Success
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                error = PurchasesError(
                    code = PurchasesErrorCode.UnknownError,
                    underlyingErrorMessage = "Error performing restore: ${t.message}"
                ).toNSError()
            } finally {
                completion(success, error)
            }
        }
    }

    override fun performPurchaseFor(
        `package`: RCPackageFromKnUi,
        completion: (userCancelled: Boolean, error: NSError?) -> Unit
    ) {
        coroutineScope.launch {
            var userCancelled = false
            var error: NSError? = null
            try {
                val result = purchaseLogic.performPurchase(
                    PaywallPurchaseLogicParams((`package` as RCPackage).toPackage())
                )
                error = (result as? PurchaseLogicResult.Error)?.errorDetails?.toNSError()
                userCancelled = result is PurchaseLogicResult.Cancellation
            } catch (e: CancellationException) {
                userCancelled = true
                throw e
            } catch (t: Throwable) {
                error = PurchasesError(
                    code = PurchasesErrorCode.UnknownError,
                    underlyingErrorMessage = "Error performing purchase: ${t.message}"
                ).toNSError()
            } finally {
                completion(userCancelled, error)
            }
        }
    }

    private fun PurchasesError.toNSError() = NSError(
        code = code.code.toLong(),
        domain = "com.revenuecat.purchases.kmp",
        userInfo = mapOf(NSLocalizedDescriptionKey to message),
    )
}
