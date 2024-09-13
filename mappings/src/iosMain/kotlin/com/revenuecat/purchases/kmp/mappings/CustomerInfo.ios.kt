package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.CustomerInfo
import platform.Foundation.dictionaryWithValuesForKeys
import platform.darwin.NSObject
import cocoapods.PurchasesHybridCommon.RCCustomerInfo as IosCustomerInfo

public fun IosCustomerInfo.toCustomerInfo(): CustomerInfo {
    @Suppress("UNCHECKED_CAST")
    return CustomerInfo(
        activeSubscriptions = activeSubscriptions() as Set<String>,
        allExpirationDateMillis = allPurchasedProductIdentifiers().associate { productId ->
            productId as String to expirationDateForProductIdentifier(productId)?.toEpochMilliseconds()
        },
        allPurchaseDateMillis = allPurchasedProductIdentifiers().associate { productId ->
            productId as String to purchaseDateForProductIdentifier(productId)?.toEpochMilliseconds()
        },
        allPurchasedProductIdentifiers = allPurchasedProductIdentifiers() as Set<String>,
        entitlements = entitlements().toEntitlementInfos(),
        firstSeenMillis = firstSeen().toEpochMilliseconds(),
        latestExpirationDateMillis = latestExpirationDate()?.toEpochMilliseconds(),
        managementUrlString = managementURL()?.absoluteString,
        nonSubscriptionTransactions = nonSubscriptions().map {
            val map = (it as NSObject)
                .dictionaryWithValuesForKeys(
                    listOf("transactionIdentifier", "productIdentifier", "purchaseDate")
                ).mapKeys { (key, _) -> key as String }
            map.toTransaction()
        },
        originalAppUserId = originalAppUserId(),
        originalApplicationVersion = null,
        originalPurchaseDateMillis = originalPurchaseDate()?.toEpochMilliseconds(),
        requestDateMillis = requestDate().toEpochMilliseconds()
    )
}
