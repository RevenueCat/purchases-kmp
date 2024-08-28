package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.PeriodUnit
import com.revenuecat.purchases.models.Period as AndroidPeriod
import com.revenuecat.purchases.models.Period.Unit as AndroidPeriodUnit

internal fun AndroidPeriod.toPeriod(): Period = Period(value, unit.toPeriodUnit())

internal fun AndroidPeriodUnit.toPeriodUnit(): PeriodUnit = when (this) {
    AndroidPeriodUnit.DAY -> PeriodUnit.DAY
    AndroidPeriodUnit.WEEK -> PeriodUnit.WEEK
    AndroidPeriodUnit.MONTH -> PeriodUnit.MONTH
    AndroidPeriodUnit.YEAR -> PeriodUnit.YEAR
    AndroidPeriodUnit.UNKNOWN -> PeriodUnit.UNKNOWN
}
