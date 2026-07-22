package com.revenuecat.purchases.kmp.mappings.ktx

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

public fun NSDate.toEpochMilliseconds(): Long =
    (timeIntervalSince1970() * 1_000).toLong()
