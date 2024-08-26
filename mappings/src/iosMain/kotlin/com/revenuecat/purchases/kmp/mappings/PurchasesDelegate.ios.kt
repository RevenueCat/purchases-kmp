package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCCustomerInfo
import cocoapods.PurchasesHybridCommon.RCPurchases
import cocoapods.PurchasesHybridCommon.RCPurchasesDelegateProtocol
import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreTransaction
import com.revenuecat.purchases.kmp.PurchasesDelegate
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.toPurchasesErrorOrThrow
import platform.Foundation.NSError
import platform.darwin.NSObject

public fun RCPurchasesDelegateProtocol.toPurchasesDelegate(): PurchasesDelegate =
    (this as PurchasesDelegateWrapper).wrapped

public fun PurchasesDelegate.toRcPurchasesDelegate(): RCPurchasesDelegateProtocol =
    PurchasesDelegateWrapper(this)

private class PurchasesDelegateWrapper(val wrapped: PurchasesDelegate) :
    RCPurchasesDelegateProtocol,
    NSObject() {


    override fun purchases(
        purchases: RCPurchases,
        readyForPromotedProduct: RCStoreProduct,
        purchase: (((RCStoreTransaction?, RCCustomerInfo?, NSError?, Boolean) -> Unit)?) -> Unit
    ) {
        wrapped.onPurchasePromoProduct(StoreProduct(readyForPromotedProduct)) { onError, onSuccess ->
            purchase { transaction, customerInfo, error, userCancelled ->
                if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
                else onSuccess(
                    transaction?.toStoreTransaction()
                        ?: error("Expected a non-null RCStoreTransaction"),
                    customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo")
                )
            }
        }
    }

    override fun purchases(purchases: RCPurchases, receivedUpdatedCustomerInfo: RCCustomerInfo) {
        wrapped.onCustomerInfoUpdated(receivedUpdatedCustomerInfo.toCustomerInfo())
    }

}
