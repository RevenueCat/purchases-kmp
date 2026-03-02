package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.Period
import com.revenuecat.purchases.kmp.models.PeriodUnit
import swiftPMImport.com.revenuecat.purchases.kn.core.RCSubscriptionPeriodUnitDay
import swiftPMImport.com.revenuecat.purchases.kn.core.RCSubscriptionPeriodUnitMonth
import swiftPMImport.com.revenuecat.purchases.kn.core.RCSubscriptionPeriodUnitWeek
import swiftPMImport.com.revenuecat.purchases.kn.core.RCSubscriptionPeriodUnitYear
import swiftPMImport.com.revenuecat.purchases.kn.core.RCSubscriptionPeriod as IosPeriod
import swiftPMImport.com.revenuecat.purchases.kn.core.RCSubscriptionPeriodUnit as IosPeriodUnit

internal fun IosPeriod.toPeriod(): Period = Period(value().toInt(), unit().toPeriodUnit())

internal fun IosPeriodUnit.toPeriodUnit(): PeriodUnit =
    when (this) {
        RCSubscriptionPeriodUnitDay -> PeriodUnit.DAY
        RCSubscriptionPeriodUnitWeek -> PeriodUnit.WEEK
        RCSubscriptionPeriodUnitMonth -> PeriodUnit.MONTH
        RCSubscriptionPeriodUnitYear -> PeriodUnit.YEAR
        else -> PeriodUnit.UNKNOWN
    }
