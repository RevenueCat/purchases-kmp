package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.PeriodType

@Suppress("unused")
private class PeriodTypeAPI {
    fun check(type: PeriodType) {
        when (type) {
            PeriodType.NORMAL,
            PeriodType.INTRO,
            PeriodType.TRIAL,
            PeriodType.PREPAID,
                -> {
            }
        }.exhaustive
    }
}
