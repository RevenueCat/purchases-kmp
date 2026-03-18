package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.EntitlementInfo
import com.revenuecat.purchases.kmp.models.EntitlementInfos
import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kmp.models.PeriodType
import com.revenuecat.purchases.kmp.models.Store
import com.revenuecat.purchases.kmp.models.SubscriptionInfo
import com.revenuecat.purchases.kmp.models.Transaction
import com.revenuecat.purchases.kmp.models.VerificationResult

/**
 * Builds a [CustomerInfo] from a PHC dictionary representation.
 * This is used when receiving CustomerInfo from the dictionary-based
 * [PaywallViewControllerDelegateWrapper] protocol.
 */
public fun buildCustomerInfo(customerInfoMap: Map<Any?, *>): Result<CustomerInfo> = runCatching {
    @Suppress("UNCHECKED_CAST")
    val activeSubscriptions = (customerInfoMap["activeSubscriptions"] as? List<*>)
        ?.filterIsInstance<String>()?.toSet() ?: emptySet()

    @Suppress("UNCHECKED_CAST")
    val allPurchasedProductIdentifiers = (customerInfoMap["allPurchasedProductIdentifiers"] as? List<*>)
        ?.filterIsInstance<String>()?.toSet() ?: emptySet()

    val allExpirationDateMillis = buildMillisMap(customerInfoMap["allExpirationDatesMillis"])
    val allPurchaseDateMillis = buildMillisMap(customerInfoMap["allPurchaseDatesMillis"])

    val entitlements = buildEntitlementInfos(customerInfoMap["entitlements"])

    val firstSeenMillis = (customerInfoMap["firstSeenMillis"] as? Number)?.toLong()
        ?: error("Expected a non-null firstSeenMillis")

    val latestExpirationDateMillis = (customerInfoMap["latestExpirationDateMillis"] as? Number)?.toLong()
    val managementUrlString = customerInfoMap["managementURL"] as? String
    val originalAppUserId = customerInfoMap["originalAppUserId"] as? String
        ?: error("Expected a non-null originalAppUserId")
    val originalApplicationVersion = customerInfoMap["originalApplicationVersion"] as? String
    val originalPurchaseDateMillis = (customerInfoMap["originalPurchaseDateMillis"] as? Number)?.toLong()
    val requestDateMillis = (customerInfoMap["requestDateMillis"] as? Number)?.toLong()
        ?: error("Expected a non-null requestDateMillis")

    @Suppress("UNCHECKED_CAST")
    val subscriptionsByProductIdentifier =
        (customerInfoMap["subscriptionsByProductIdentifier"] as? Map<String, Map<String, Any?>>)
            ?.mapValues { (_, v) -> buildSubscriptionInfoFromDict(v) } ?: emptyMap()

    @Suppress("UNCHECKED_CAST")
    val nonSubscriptionTransactions =
        (customerInfoMap["nonSubscriptionTransactions"] as? List<Map<String, Any?>>)
            ?.map { buildTransactionFromDict(it) } ?: emptyList()

    CustomerInfo(
        activeSubscriptions = activeSubscriptions,
        allExpirationDateMillis = allExpirationDateMillis,
        allPurchaseDateMillis = allPurchaseDateMillis,
        allPurchasedProductIdentifiers = allPurchasedProductIdentifiers,
        entitlements = entitlements,
        firstSeenMillis = firstSeenMillis,
        latestExpirationDateMillis = latestExpirationDateMillis,
        managementUrlString = managementUrlString,
        subscriptionsByProductIdentifier = subscriptionsByProductIdentifier,
        nonSubscriptionTransactions = nonSubscriptionTransactions,
        originalAppUserId = originalAppUserId,
        originalApplicationVersion = originalApplicationVersion,
        originalPurchaseDateMillis = originalPurchaseDateMillis,
        requestDateMillis = requestDateMillis,
    )
}

private fun buildMillisMap(raw: Any?): Map<String, Long?> {
    @Suppress("UNCHECKED_CAST")
    val map = raw as? Map<String, Any?> ?: return emptyMap()
    return map.mapValues { (_, v) -> (v as? Number)?.toLong() }
}

@Suppress("UNCHECKED_CAST")
private fun buildEntitlementInfos(raw: Any?): EntitlementInfos {
    val map = raw as? Map<String, Any?> ?: return EntitlementInfos(
        all = emptyMap(),
        verification = VerificationResult.NOT_REQUESTED,
    )
    val allMap = (map["all"] as? Map<String, Map<String, Any?>>) ?: emptyMap()
    val all = allMap.mapValues { (_, v) -> buildEntitlementInfoFromDict(v) }
    val verification = parseVerificationResult(map["verification"] as? String)
    return EntitlementInfos(all = all, verification = verification)
}

private fun buildEntitlementInfoFromDict(map: Map<String, Any?>): EntitlementInfo =
    EntitlementInfo(
        identifier = map["identifier"] as? String ?: "",
        isActive = map["isActive"] as? Boolean ?: false,
        willRenew = map["willRenew"] as? Boolean ?: false,
        periodType = parsePeriodType(map["periodType"] as? String),
        latestPurchaseDateMillis = (map["latestPurchaseDateMillis"] as? Number)?.toLong(),
        originalPurchaseDateMillis = (map["originalPurchaseDateMillis"] as? Number)?.toLong(),
        expirationDateMillis = (map["expirationDateMillis"] as? Number)?.toLong(),
        store = parseStore(map["store"] as? String),
        productIdentifier = map["productIdentifier"] as? String ?: "",
        productPlanIdentifier = map["productPlanIdentifier"] as? String,
        isSandbox = map["isSandbox"] as? Boolean ?: false,
        unsubscribeDetectedAtMillis = (map["unsubscribeDetectedAtMillis"] as? Number)?.toLong(),
        billingIssueDetectedAtMillis = (map["billingIssueDetectedAtMillis"] as? Number)?.toLong(),
        ownershipType = parseOwnershipType(map["ownershipType"] as? String),
        verification = parseVerificationResult(map["verification"] as? String),
    )

private fun buildSubscriptionInfoFromDict(map: Map<String, Any?>): SubscriptionInfo =
    SubscriptionInfo(
        productIdentifier = map["productIdentifier"] as? String ?: "",
        purchaseDateMillis = (map["purchaseDateMillis"] as? Number)?.toLong()
            ?: error("Expected a non-null purchaseDateMillis in subscriptionInfo"),
        originalPurchaseDateMillis = (map["originalPurchaseDateMillis"] as? Number)?.toLong(),
        expiresDateMillis = (map["expiresDateMillis"] as? Number)?.toLong(),
        store = parseStore(map["store"] as? String),
        isSandbox = map["isSandbox"] as? Boolean ?: false,
        unsubscribeDetectedAtMillis = (map["unsubscribeDetectedAtMillis"] as? Number)?.toLong(),
        billingIssuesDetectedAtMillis = (map["billingIssuesDetectedAtMillis"] as? Number)?.toLong(),
        gracePeriodExpiresDateMillis = (map["gracePeriodExpiresDateMillis"] as? Number)?.toLong(),
        ownershipType = parseOwnershipType(map["ownershipType"] as? String),
        periodType = parsePeriodType(map["periodType"] as? String),
        refundedAtMillis = (map["refundedAtMillis"] as? Number)?.toLong(),
        storeTransactionId = map["storeTransactionId"] as? String,
        autoResumeDateMillis = null,
        price = null, // Price is not reliably available from PHC dictionary format
        productPlanIdentifier = null,
        managementUrlString = null,
        isActive = map["isActive"] as? Boolean ?: false,
        willRenew = map["willRenew"] as? Boolean ?: false,
    )

private fun buildTransactionFromDict(map: Map<String, Any?>): Transaction =
    Transaction(
        transactionIdentifier = map["transactionIdentifier"] as? String ?: "",
        productIdentifier = map["productIdentifier"] as? String ?: "",
        purchaseDateMillis = (map["purchaseDateMillis"] as? Number)?.toLong() ?: 0L,
    )

internal fun parseStore(value: String?): Store = when (value) {
    "APP_STORE" -> Store.APP_STORE
    "MAC_APP_STORE" -> Store.MAC_APP_STORE
    "PLAY_STORE" -> Store.PLAY_STORE
    "STRIPE" -> Store.STRIPE
    "PROMOTIONAL" -> Store.PROMOTIONAL
    "AMAZON" -> Store.AMAZON
    "RC_BILLING" -> Store.RC_BILLING
    "EXTERNAL" -> Store.EXTERNAL
    "PADDLE" -> Store.PADDLE
    "TEST_STORE" -> Store.TEST_STORE
    "GALAXY" -> Store.GALAXY
    else -> Store.UNKNOWN_STORE
}

internal fun parsePeriodType(value: String?): PeriodType = when (value) {
    "NORMAL" -> PeriodType.NORMAL
    "INTRO" -> PeriodType.INTRO
    "TRIAL" -> PeriodType.TRIAL
    "PREPAID" -> PeriodType.PREPAID
    else -> PeriodType.NORMAL
}

internal fun parseOwnershipType(value: String?): OwnershipType = when (value) {
    "PURCHASED" -> OwnershipType.PURCHASED
    "FAMILY_SHARED" -> OwnershipType.FAMILY_SHARED
    else -> OwnershipType.UNKNOWN
}

internal fun parseVerificationResult(value: String?): VerificationResult = when (value) {
    "VERIFIED" -> VerificationResult.VERIFIED
    "VERIFIED_ON_DEVICE" -> VerificationResult.VERIFIED_ON_DEVICE
    "FAILED" -> VerificationResult.FAILED
    else -> VerificationResult.NOT_REQUESTED
}
