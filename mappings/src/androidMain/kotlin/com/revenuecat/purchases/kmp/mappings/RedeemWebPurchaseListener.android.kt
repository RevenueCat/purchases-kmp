package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.ExperimentalPreviewRevenueCatPurchasesAPI
import com.revenuecat.purchases.kmp.models.RedeemWebPurchaseListener
import com.revenuecat.purchases.interfaces.RedeemWebPurchaseListener as NativeRedeemWebPurchaseListener

@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun NativeRedeemWebPurchaseListener.Result.toWebPurchaseResult(): RedeemWebPurchaseListener.Result {
    return when (this) {
        is NativeRedeemWebPurchaseListener.Result.Success ->
            RedeemWebPurchaseListener.Result.Success(customerInfo.toCustomerInfo())
        is NativeRedeemWebPurchaseListener.Result.Error ->
            RedeemWebPurchaseListener.Result.Error(error.toPurchasesError())
        NativeRedeemWebPurchaseListener.Result.InvalidToken ->
            RedeemWebPurchaseListener.Result.InvalidToken
        is NativeRedeemWebPurchaseListener.Result.Expired ->
            RedeemWebPurchaseListener.Result.Expired(obfuscatedEmail)
        NativeRedeemWebPurchaseListener.Result.PurchaseBelongsToOtherUser ->
            RedeemWebPurchaseListener.Result.PurchaseBelongsToOtherUser
    }
}
