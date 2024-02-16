@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package io.shortway.kobankat

import cocoapods.RevenueCat.RCCustomerInfo
import cocoapods.RevenueCat.RCPurchases
import cocoapods.RevenueCat.RCPurchasesDelegateProtocol
import cocoapods.RevenueCat.RCStoreProduct
import cocoapods.RevenueCat.RCStoreTransaction
import platform.Foundation.NSError
import platform.darwin.NSObject

internal fun RCPurchasesDelegateProtocol.toPurchasesDelegate(): PurchasesDelegate =
    (this as PurchasesDelegateWrapper).wrapped

internal fun PurchasesDelegate.toRcPurchasesDelegate(): RCPurchasesDelegateProtocol =
    PurchasesDelegateWrapper(this)

private class PurchasesDelegateWrapper(val wrapped: PurchasesDelegate) :
    RCPurchasesDelegateProtocol,
    NSObject() {


    override fun purchases(
        purchases: RCPurchases,
        readyForPromotedProduct: RCStoreProduct,
        purchase: (((RCStoreTransaction?, RCCustomerInfo?, NSError?, Boolean) -> Unit)?) -> Unit
    ) {
        wrapped.onPurchasePromoProduct(readyForPromotedProduct) { onError, onSuccess ->
            purchase { transaction, customerInfo, error, userCancelled ->
                if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
                else onSuccess(
                    transaction ?: error("Expected a non-null RCStoreTransaction"),
                    customerInfo ?: error("Expected a non-null RCCustomerInfo")
                )
            }
        }
    }

    override fun purchases(purchases: RCPurchases, receivedUpdatedCustomerInfo: RCCustomerInfo) {
        wrapped.onCustomerInfoUpdated(receivedUpdatedCustomerInfo)
    }

}