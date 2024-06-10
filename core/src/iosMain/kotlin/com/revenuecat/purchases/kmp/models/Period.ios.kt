package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriod
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnit
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitDay
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitMonth
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitWeek
import cocoapods.PurchasesHybridCommon.RCSubscriptionPeriodUnitYear

public actual typealias Period = RCSubscriptionPeriod
public actual enum class PeriodUnit {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    UNKNOWN,
}

public actual val Period.value: Int
    get() = value().toInt()

public actual val Period.unit: PeriodUnit
    get() = unit().toUnit()

internal fun RCSubscriptionPeriodUnit.toUnit(): PeriodUnit =
    when (this) {
        RCSubscriptionPeriodUnitDay -> PeriodUnit.DAY
        RCSubscriptionPeriodUnitWeek -> PeriodUnit.WEEK
        RCSubscriptionPeriodUnitMonth -> PeriodUnit.MONTH
        RCSubscriptionPeriodUnitYear -> PeriodUnit.YEAR
        else -> PeriodUnit.UNKNOWN
    }
