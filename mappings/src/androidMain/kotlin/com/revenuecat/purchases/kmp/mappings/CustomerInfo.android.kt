package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.CustomerInfo as AndroidCustomerInfo

public fun AndroidCustomerInfo.toCustomerInfo(): CustomerInfo {
    return CustomerInfo(
        activeSubscriptions,
        allExpirationDatesByProduct.mapValues { (_, date) -> date?.time },
        allPurchaseDatesByProduct.mapValues { (_, date) -> date?.time },
        allPurchaseDatesByProduct.keys,
        entitlements.toEntitlementInfos(),
        firstSeen.time,
        latestExpirationDate?.time,
        managementURL?.toString(),
        nonSubscriptionTransactions.map { it.toTransaction() },
        originalAppUserId,
        null,
        originalPurchaseDate?.time,
        requestDate.time
    )
}
