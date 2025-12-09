package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.SubscriptionInfo
import com.revenuecat.purchases.kmp.models.SubscriptionPrice
import com.revenuecat.purchases.SubscriptionInfo as AndroidSubscriptionInfo

public fun AndroidSubscriptionInfo.toSubscriptionInfo(): SubscriptionInfo {
    return SubscriptionInfo(
        productIdentifier = productIdentifier,
        purchaseDateMillis = purchaseDate.time,
        originalPurchaseDateMillis = originalPurchaseDate?.time,
        expiresDateMillis = expiresDate?.time,
        store = store.toStore(),
        isSandbox = isSandbox,
        unsubscribeDetectedAtMillis = unsubscribeDetectedAt?.time,
        billingIssuesDetectedAtMillis = billingIssuesDetectedAt?.time,
        gracePeriodExpiresDateMillis = gracePeriodExpiresDate?.time,
        ownershipType = ownershipType.toOwnershipType(),
        periodType = periodType.toPeriodType(),
        refundedAtMillis = refundedAt?.time,
        storeTransactionId = storeTransactionId,
        autoResumeDateMillis = autoResumeDate?.time,
        price = price?.let {
            SubscriptionPrice(
                currency = it.currencyCode,
                amount = it.amountMicros.toDouble() / 1_000_000.0,
                formatted = it.formatted
            )
        },
        productPlanIdentifier = productPlanIdentifier,
        managementUrlString = managementURL?.toString(),
        isActive = isActive,
        willRenew = willRenew,
    )
}