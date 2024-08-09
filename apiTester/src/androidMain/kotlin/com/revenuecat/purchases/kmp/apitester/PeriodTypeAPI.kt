package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PeriodType

@Suppress("unused")
private class PeriodTypeAPI {
    fun check(type: PeriodType) {
        when (type) {
            PeriodType.NORMAL,
            PeriodType.INTRO,
            PeriodType.TRIAL,
            -> {
            }
        }.exhaustive
    }
}
