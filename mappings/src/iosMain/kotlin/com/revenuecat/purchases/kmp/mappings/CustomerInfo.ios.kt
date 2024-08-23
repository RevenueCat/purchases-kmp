package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.Transaction
import platform.Foundation.dictionaryWithValuesForKeys
import platform.darwin.NSObject
import cocoapods.PurchasesHybridCommon.RCCustomerInfo as IosCustomerInfo

public fun IosCustomerInfo.toCustomerInfo(): CustomerInfo {
    @Suppress("UNCHECKED_CAST")
    return CustomerInfo(
        activeSubscriptions() as Set<String>,
        allPurchasedProductIdentifiers().associate { productId ->
            productId as String to expirationDateForProductIdentifier(productId)?.toEpochMilliseconds()
        },
        allPurchasedProductIdentifiers().associate { productId ->
            productId as String to purchaseDateForProductIdentifier(productId)?.toEpochMilliseconds()
        },
        allPurchasedProductIdentifiers() as Set<String>,
        entitlements().toEntitlementInfos(),
        firstSeen().toEpochMilliseconds(),
        latestExpirationDate()?.toEpochMilliseconds(),
        managementURL()?.absoluteString,
        nonSubscriptions().map {
            val map = (it as NSObject)
                .dictionaryWithValuesForKeys(
                    listOf("transactionIdentifier", "productIdentifier", "purchaseDate")
                ).mapKeys { (key, _) -> key as String }
            map.toTransaction()
        },
        originalAppUserId(),
        null,
        originalPurchaseDate()?.toEpochMilliseconds(),
        requestDate().toEpochMilliseconds()
    )
}
