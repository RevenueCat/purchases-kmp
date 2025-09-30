package com.revenuecat.purchases.kmp.datetime

import com.revenuecat.purchases.kmp.models.Transaction
import kotlinx.datetime.Instant

/**
 * The purchase instant.
 */
@Deprecated(
    message = "Instant properties have been moved to member fields. The entire " +
            "purchases-kmp-datetime module is deprecated and can be removed from your Gradle " +
            "files.",
    replaceWith = ReplaceWith("purchaseDate"),
)
public val Transaction.purchaseInstant: Instant
    get() = Instant.fromEpochMilliseconds(purchaseDateMillis)
