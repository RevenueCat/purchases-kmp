package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitDay
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitMonth
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitWeek
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitYear
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriod as IosPeriod
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnit as IosPeriodUnit

public actual class Period(
    internal val wrapped: IosPeriod
) {
    public actual val value: Int = wrapped.value().toInt()
    public actual val unit: PeriodUnit = wrapped.unit().toPeriodUnit()
}

internal fun IosPeriodUnit.toPeriodUnit(): PeriodUnit =
    when (this) {
        RCSubscriptionPeriodUnitDay -> PeriodUnit.DAY
        RCSubscriptionPeriodUnitWeek -> PeriodUnit.WEEK
        RCSubscriptionPeriodUnitMonth -> PeriodUnit.MONTH
        RCSubscriptionPeriodUnitYear -> PeriodUnit.YEAR
        else -> PeriodUnit.UNKNOWN
    }
