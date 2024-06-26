package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCCustomerInfo
import com.revenuecat.purchases.kmp.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.Transaction
import platform.Foundation.dictionaryWithValuesForKeys
import platform.darwin.NSObject

public actual typealias CustomerInfo = RCCustomerInfo

@Suppress("UNCHECKED_CAST")
public actual val CustomerInfo.activeSubscriptions: Set<String>
    get() = activeSubscriptions() as Set<String>
public actual val CustomerInfo.allExpirationDateMillis: Map<String, Long?>
    get() = allPurchasedProductIdentifiers().associate { productId ->
        productId as String to expirationDateForProductIdentifier(productId)?.toEpochMilliseconds()
    }
public actual val CustomerInfo.allPurchaseDateMillis: Map<String, Long?>
    get() = allPurchasedProductIdentifiers().associate { productId ->
        productId as String to purchaseDateForProductIdentifier(productId)?.toEpochMilliseconds()
    }
@Suppress("UNCHECKED_CAST")
public actual val CustomerInfo.allPurchasedProductIdentifiers: Set<String>
    get() = allPurchasedProductIdentifiers() as Set<String>
public actual val CustomerInfo.entitlements: EntitlementInfos
    get() = entitlements()
public actual val CustomerInfo.firstSeenMillis: Long
    get() = firstSeen().toEpochMilliseconds()
public actual val CustomerInfo.latestExpirationDateMillis: Long?
    get() = latestExpirationDate()?.toEpochMilliseconds()
public actual val CustomerInfo.managementUrlString: String?
    get() = managementURL()?.absoluteString
public actual val CustomerInfo.nonSubscriptionTransactions: List<Transaction>
    get() = nonSubscriptions().map {
        val map = (it as NSObject)
            .dictionaryWithValuesForKeys(
                listOf("transactionIdentifier", "productIdentifier", "purchaseDate")
            ).mapKeys { (key, _) -> key as String }
        Transaction(map)
    }
public actual val CustomerInfo.originalAppUserId: String
    get() = originalAppUserId()
public actual val CustomerInfo.originalApplicationVersion: String?
    get() = originalApplicationVersion()
public actual val CustomerInfo.originalPurchaseDateMillis: Long?
    get() = originalPurchaseDate()?.toEpochMilliseconds()
public actual val CustomerInfo.requestDateMillis: Long
    get() = requestDate().toEpochMilliseconds()
