package io.shortway.kobankat.models

private const val DAYS_PER_WEEK = 7.0
private const val DAYS_PER_MONTH = 30.0
private const val DAYS_PER_YEAR = 365.0
private const val MONTHS_PER_YEAR = 12.0
private const val WEEKS_PER_MONTH = DAYS_PER_YEAR / MONTHS_PER_YEAR / DAYS_PER_WEEK

public expect class Period
public expect enum class PeriodUnit {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    UNKNOWN,
}

/**
 * The number of period units.
 */
public expect val Period.value: Int

/**
 * The increment of time that a subscription period is specified in.
 */
public expect val Period.unit: PeriodUnit

public val Period.valueInMonths: Double
    get() = when (unit) {
        PeriodUnit.DAY -> value / DAYS_PER_MONTH
        PeriodUnit.WEEK -> value / WEEKS_PER_MONTH
        PeriodUnit.MONTH -> value.toDouble()
        PeriodUnit.YEAR -> value * MONTHS_PER_YEAR
        PeriodUnit.UNKNOWN -> 0.0
        else -> error("Unexpected PeriodUnit: $unit")
    }