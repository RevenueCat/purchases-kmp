package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.Transaction
import platform.Foundation.NSDate

internal fun Map<String, Any?>.toTransaction(): Transaction {
    // TODO: This is a workaround for the fact that PHC does not expose
    // the NonSubscriptionTransaction type
    return Transaction(
        get("transactionIdentifier") as String,
        get("productIdentifier") as String,
        (get("purchaseDate") as NSDate).toEpochMilliseconds()
    )
}
