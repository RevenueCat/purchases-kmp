package io.shortway.kobankat.apitester

import io.shortway.kobankat.models.Period
import io.shortway.kobankat.models.PeriodUnit
import io.shortway.kobankat.models.unit
import io.shortway.kobankat.models.value
import io.shortway.kobankat.models.valueInMonths

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
