package io.shortway.kobankat.datetime

import io.shortway.kobankat.models.Transaction
import io.shortway.kobankat.models.purchaseDateMillis
import kotlinx.datetime.Instant

/**
 * The purchase instant.
 */
public val Transaction.purchaseInstant: Instant
    get() = Instant.fromEpochMilliseconds(purchaseDateMillis)
