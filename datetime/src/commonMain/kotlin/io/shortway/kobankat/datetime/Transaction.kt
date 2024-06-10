package io.shortway.kobankat.datetime

import com.revenuecat.purchases.kmp.models.Transaction
import com.revenuecat.purchases.kmp.models.purchaseDateMillis
import kotlinx.datetime.Instant

/**
 * The purchase instant.
 */
public val Transaction.purchaseInstant: Instant
    get() = Instant.fromEpochMilliseconds(purchaseDateMillis)
