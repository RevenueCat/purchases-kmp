package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.Period as AndroidPeriod
import com.revenuecat.purchases.models.Period.Unit as AndroidPeriodUnit

public actual class Period(
    internal val wrapped: AndroidPeriod
) {
    public actual val value: Int = wrapped.value
    public actual val unit: PeriodUnit = wrapped.unit.toPeriodUnit()
}

internal fun AndroidPeriodUnit.toPeriodUnit(): PeriodUnit = when (this) {
    AndroidPeriodUnit.DAY -> PeriodUnit.DAY
    AndroidPeriodUnit.WEEK -> PeriodUnit.WEEK
    AndroidPeriodUnit.MONTH -> PeriodUnit.MONTH
    AndroidPeriodUnit.YEAR -> PeriodUnit.YEAR
    AndroidPeriodUnit.UNKNOWN -> PeriodUnit.UNKNOWN
}
