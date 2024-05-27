package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCCustomerInfo
import io.shortway.kobankat.ktx.toEpochMilliseconds
import io.shortway.kobankat.models.Transaction

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
@Suppress("UNCHECKED_CAST")
public actual val CustomerInfo.nonSubscriptionTransactions: List<Transaction>
    get() = nonSubscriptions() as List<Transaction>
public actual val CustomerInfo.originalAppUserId: String
    get() = originalAppUserId()
public actual val CustomerInfo.originalApplicationVersion: String?
    get() = originalApplicationVersion()
public actual val CustomerInfo.originalPurchaseDateMillis: Long?
    get() = originalPurchaseDate()?.toEpochMilliseconds()
public actual val CustomerInfo.requestDateMillis: Long
    get() = requestDate().toEpochMilliseconds()
