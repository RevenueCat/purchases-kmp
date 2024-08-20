package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.PeriodUnit
import com.revenuecat.purchases.kmp.models.valueInMonths

@Suppress("unused", "UNUSED_VARIABLE")
private class PeriodAPI {
    fun check(period: Period) {
        with(period) {
            val value: Int = value
            val unit: PeriodUnit = unit
            val valueInMonths: Double = valueInMonths
        }
    }
}
