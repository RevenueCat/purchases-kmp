package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.CustomerInfo as AndroidCustomerInfo

public fun AndroidCustomerInfo.toCustomerInfo(): CustomerInfo {
    return CustomerInfo(
        activeSubscriptions = activeSubscriptions,
        allExpirationDateMillis = allExpirationDatesByProduct.mapValues { (_, date) -> date?.time },
        allPurchaseDateMillis = allPurchaseDatesByProduct.mapValues { (_, date) -> date?.time },
        allPurchasedProductIdentifiers = allPurchaseDatesByProduct.keys,
        entitlements = entitlements.toEntitlementInfos(),
        firstSeenMillis = firstSeen.time,
        latestExpirationDateMillis = latestExpirationDate?.time,
        managementUrlString = managementURL?.toString(),
        nonSubscriptionTransactions = nonSubscriptionTransactions.map { it.toTransaction() },
        originalAppUserId = originalAppUserId,
        originalApplicationVersion = null,
        originalPurchaseDateMillis = originalPurchaseDate?.time,
        requestDateMillis = requestDate.time
    )
}
