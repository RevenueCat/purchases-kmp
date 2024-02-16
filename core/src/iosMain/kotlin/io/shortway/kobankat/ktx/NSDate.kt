package io.shortway.kobankat.ktx

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

internal fun NSDate.toEpochMilliseconds(): Long =
    (timeIntervalSince1970() * 1_000).toLong()