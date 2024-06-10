@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCCustomerInfo
import cocoapods.PurchasesHybridCommon.RCPurchases
import cocoapods.PurchasesHybridCommon.RCPurchasesDelegateProtocol
import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreTransaction
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
