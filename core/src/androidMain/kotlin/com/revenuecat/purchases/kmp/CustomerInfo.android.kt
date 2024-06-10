@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.Transaction
import com.revenuecat.purchases.CustomerInfo as RcCustomerInfo

public actual typealias CustomerInfo = RcCustomerInfo

public actual val CustomerInfo.activeSubscriptions: Set<String>
    get() = activeSubscriptions
public actual val CustomerInfo.allExpirationDateMillis: Map<String, Long?>
    get() = allExpirationDatesByProduct.mapValues { (_, date) -> date?.time }
public actual val CustomerInfo.allPurchaseDateMillis: Map<String, Long?>
    get() = allPurchaseDatesByProduct.mapValues { (_, date) -> date?.time }
public actual val CustomerInfo.allPurchasedProductIdentifiers: Set<String>
    get() = allPurchaseDatesByProduct.keys
public actual val CustomerInfo.entitlements: EntitlementInfos
    get() = entitlements
public actual val CustomerInfo.firstSeenMillis: Long
    get() = firstSeen.time
public actual val CustomerInfo.latestExpirationDateMillis: Long?
    get() = latestExpirationDate?.time
public actual val CustomerInfo.managementUrlString: String?
    get() = managementURL?.toString()
public actual val CustomerInfo.nonSubscriptionTransactions: List<Transaction>
    get() = nonSubscriptionTransactions
public actual val CustomerInfo.originalAppUserId: String
    get() = originalAppUserId
public actual val CustomerInfo.originalApplicationVersion: String?
    get() = null
public actual val CustomerInfo.originalPurchaseDateMillis: Long?
    get() = originalPurchaseDate?.time
public actual val CustomerInfo.requestDateMillis: Long
    get() = requestDate.time
