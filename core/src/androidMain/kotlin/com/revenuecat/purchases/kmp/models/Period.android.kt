@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.Period as RcPeriod

public actual typealias Period = RcPeriod
public actual typealias PeriodUnit = RcPeriod.Unit

public actual val Period.value: Int
    get() = value

public actual val Period.unit: PeriodUnit
    get() = unit
