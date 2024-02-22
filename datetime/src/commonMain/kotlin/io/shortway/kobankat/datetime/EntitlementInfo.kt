package io.shortway.kobankat.datetime

import io.shortway.kobankat.EntitlementInfo
import io.shortway.kobankat.PeriodType
import io.shortway.kobankat.billingIssueDetectedAtMillis
import io.shortway.kobankat.expirationDateMillis
import io.shortway.kobankat.isActive
import io.shortway.kobankat.latestPurchaseDateMillis
import io.shortway.kobankat.originalPurchaseDateMillis
import io.shortway.kobankat.periodType
import io.shortway.kobankat.unsubscribeDetectedAtMillis
import kotlinx.datetime.Instant


/**
 * Nullable on iOS only not on Android. The latest purchase or renewal instant for the entitlement.
 */
public val EntitlementInfo.latestPurchaseInstant: Instant?
    get() = latestPurchaseDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * Nullable on iOS only not on Android. The first instant this entitlement was purchased.
 */
public val EntitlementInfo.originalPurchaseInstant: Instant?
    get() = originalPurchaseDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The expiration instant for the entitlement, can be `null` for lifetime access. If the
 * [periodType] is [PeriodType.TRIAL], this is the trial expiration instant.
 */
public val EntitlementInfo.expirationInstant: Instant?
    get() = expirationDateMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The instant an unsubscribe was detected. Can be `null`.
 *
 * Note: Entitlement may still be active even if user has unsubscribed. Check the [isActive]
 * property.
 */
public val EntitlementInfo.unsubscribeDetectedAtInstant: Instant?
    get() = unsubscribeDetectedAtMillis?.let { Instant.fromEpochMilliseconds(it) }

/**
 * The instant a billing issue was detected. Can be `null` if there is no billing issue or an issue
 * has been resolved. Note: Entitlement may still be active even if there is a billing issue.
 * Check the [isActive] property.
 */
public val EntitlementInfo.billingIssueDetectedAtInstant: Instant?
    get() = billingIssueDetectedAtMillis?.let { Instant.fromEpochMilliseconds(it) }
