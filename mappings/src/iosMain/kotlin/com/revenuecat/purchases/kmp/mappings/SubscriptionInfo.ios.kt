package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.models.SubscriptionInfo
import platform.Foundation.NSDate
import platform.Foundation.NSURL
import platform.Foundation.valueForKey
import platform.darwin.NSObject
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPeriodType
import swiftPMImport.com.revenuecat.purchases.kn.core.RCPurchaseOwnershipType
import swiftPMImport.com.revenuecat.purchases.kn.core.RCStore

internal fun Any.toSubscriptionInfo(): SubscriptionInfo {
    val obj = this as NSObject

    val priceObj = obj.valueForKey("price") as? NSObject
    val subscriptionPrice = priceObj?.let {
        val currency = it.valueForKey("currency") as? String
        val amount = it.valueForKey("amount") as? Double
        val formatted = it.valueForKey("formatted") as? String
        if (currency != null && amount != null && formatted != null) {
            Price(
                currencyCode = currency,
                amountMicros = (amount * 1_000_000.0).toLong(),
                formatted = formatted
            )
        } else {
            null
        }
    }

    return SubscriptionInfo(
        productIdentifier = obj.valueForKey("productIdentifier") as String,
        purchaseDateMillis = (obj.valueForKey("purchaseDate") as NSDate).toEpochMilliseconds(),
        originalPurchaseDateMillis = (obj.valueForKey("originalPurchaseDate") as? NSDate)?.toEpochMilliseconds(),
        expiresDateMillis = (obj.valueForKey("expiresDate") as? NSDate)?.toEpochMilliseconds(),
        store = (obj.valueForKey("store") as RCStore).toStore(),
        isSandbox = obj.valueForKey("isSandbox") as Boolean,
        unsubscribeDetectedAtMillis = (obj.valueForKey("unsubscribeDetectedAt") as? NSDate)?.toEpochMilliseconds(),
        billingIssuesDetectedAtMillis = (obj.valueForKey("billingIssuesDetectedAt") as? NSDate)?.toEpochMilliseconds(),
        gracePeriodExpiresDateMillis = (obj.valueForKey("gracePeriodExpiresDate") as? NSDate)?.toEpochMilliseconds(),
        ownershipType = (obj.valueForKey("ownershipType") as RCPurchaseOwnershipType).toOwnershipType(),
        periodType = (obj.valueForKey("periodType") as RCPeriodType).toPeriodType(),
        refundedAtMillis = (obj.valueForKey("refundedAt") as? NSDate)?.toEpochMilliseconds(),
        storeTransactionId = obj.valueForKey("storeTransactionId") as? String,
        autoResumeDateMillis = null, // Not available on iOS
        price = subscriptionPrice,
        productPlanIdentifier = null, // Not available on iOS
        managementUrlString = (obj.valueForKey("managementURL") as? NSURL)?.absoluteString,
        isActive = obj.valueForKey("isActive") as Boolean,
        willRenew = obj.valueForKey("willRenew") as Boolean
    )
}
