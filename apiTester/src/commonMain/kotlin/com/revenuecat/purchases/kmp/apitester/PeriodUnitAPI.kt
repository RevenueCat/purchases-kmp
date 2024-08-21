package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.PeriodUnit

@Suppress("unused")
private class PeriodUnitAPI {
    fun check(unit: PeriodUnit) {
        when (unit) {
            PeriodUnit.DAY,
            PeriodUnit.WEEK,
            PeriodUnit.MONTH,
            PeriodUnit.YEAR,
            PeriodUnit.UNKNOWN,
            -> {
            }
        }.exhaustive
    }
}
