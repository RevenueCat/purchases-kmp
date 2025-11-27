package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCSubscriptionPeriod as IosPeriod
import swiftPMImport.com.revenuecat.purchases.models.RCSubscriptionPeriodUnit as IosPeriodUnit
import swiftPMImport.com.revenuecat.purchases.models.RCSubscriptionPeriodUnitDay
import swiftPMImport.com.revenuecat.purchases.models.RCSubscriptionPeriodUnitMonth
import swiftPMImport.com.revenuecat.purchases.models.RCSubscriptionPeriodUnitWeek
import swiftPMImport.com.revenuecat.purchases.models.RCSubscriptionPeriodUnitYear
import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.PeriodUnit

internal fun IosPeriod.toPeriod(): Period = Period(value().toInt(), unit().toPeriodUnit())

internal fun IosPeriodUnit.toPeriodUnit(): PeriodUnit =
    when (this) {
        RCSubscriptionPeriodUnitDay -> PeriodUnit.DAY
        RCSubscriptionPeriodUnitWeek -> PeriodUnit.WEEK
        RCSubscriptionPeriodUnitMonth -> PeriodUnit.MONTH
        RCSubscriptionPeriodUnitYear -> PeriodUnit.YEAR
        else -> PeriodUnit.UNKNOWN
    }
