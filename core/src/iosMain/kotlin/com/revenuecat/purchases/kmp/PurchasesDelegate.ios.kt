package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCCustomerInfo
import cocoapods.PurchasesHybridCommon.RCPurchases
import cocoapods.PurchasesHybridCommon.RCPurchasesDelegateProtocol
import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.RCStoreTransaction
import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toPurchasesErrorOrThrow
import com.revenuecat.purchases.kmp.mappings.toStoreProduct
import com.revenuecat.purchases.kmp.mappings.toStoreTransaction
import platform.Foundation.NSError
import platform.darwin.NSObject

internal fun RCPurchasesDelegateProtocol.toPurchasesDelegate(): PurchasesDelegate =
    (this as PurchasesDelegateWrapper).wrapped

internal fun PurchasesDelegate?.toRcPurchasesDelegate(): RCPurchasesDelegateProtocol? =
    this?.let { PurchasesDelegateWrapper(it) }
        .also { PurchasesDelegateStrongReference.delegate = it }

private class PurchasesDelegateWrapper(val wrapped: PurchasesDelegate) :
    RCPurchasesDelegateProtocol,
    NSObject() {


    override fun purchases(
        purchases: RCPurchases,
        readyForPromotedProduct: RCStoreProduct,
        purchase: (((RCStoreTransaction?, RCCustomerInfo?, NSError?, Boolean) -> Unit)?) -> Unit
    ) {
        wrapped.onPurchasePromoProduct(readyForPromotedProduct.toStoreProduct()) { onError, onSuccess ->
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

/**
 * On the iOS platform side, the backing field of `Purchases.delegate`, `Purchases.privateDelegate`,
 * is a `weak var`. On the multiplatform side, we wrap the actual delegate in
 * `PurchasesDelegateWrapper` to conform to `RCPurchasesDelegateProtocol`. Since that is a private
 * implementation detail, the only reference held to our wrapper is the `weak var`. This means that
 * it gets deallocated the first chance it gets. For this reason, we keep a strong reference to our
 * `PurchasesDelegateWrapper` here. This matches the Android behavior, which keeps a strong
 * reference on the platform side already.
 *
 * **Note**: we cannot make our `PurchasesDelegateWrapper` an `object` directly, as objects cannot
 * extend `NSObject`. See also
 * [KT67930](https://youtrack.jetbrains.com/issue/KT-67930/Getting-Crash-while-building-KMM-project-with-XCode-15.3#focus=Comments-27-9796215.0-0)
 */
private object PurchasesDelegateStrongReference {
    var delegate: RCPurchasesDelegateProtocol? = null
}
