package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriod as IosPeriod
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnit as IosPeriodUnit
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitDay
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitMonth
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitWeek
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitYear

internal fun IosPeriod.toPeriod(): Period = Period(value().toInt(), unit().toPeriodUnit())

internal fun IosPeriodUnit.toPeriodUnit(): PeriodUnit =
    when (this) {
        RCSubscriptionPeriodUnitDay -> PeriodUnit.DAY
        RCSubscriptionPeriodUnitWeek -> PeriodUnit.WEEK
        RCSubscriptionPeriodUnitMonth -> PeriodUnit.MONTH
        RCSubscriptionPeriodUnitYear -> PeriodUnit.YEAR
        else -> PeriodUnit.UNKNOWN
    }
