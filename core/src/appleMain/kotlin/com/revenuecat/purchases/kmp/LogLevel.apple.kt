package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kn.core.RCLogLevelDebug
import com.revenuecat.purchases.kn.core.RCLogLevelError
import com.revenuecat.purchases.kn.core.RCLogLevelInfo
import com.revenuecat.purchases.kn.core.RCLogLevelVerbose
import com.revenuecat.purchases.kn.core.RCLogLevelWarn
import com.revenuecat.purchases.kn.core.RCLogLevel as IosLogLevel

internal fun IosLogLevel.toLogLevel(): LogLevel =
    when (this) {
        RCLogLevelVerbose -> LogLevel.VERBOSE
        RCLogLevelDebug -> LogLevel.DEBUG
        RCLogLevelInfo -> LogLevel.INFO
        RCLogLevelWarn -> LogLevel.WARN
        RCLogLevelError -> LogLevel.ERROR
        else -> error("Unexpected IosLogLevel: $this")
    }

internal fun LogLevel.toRcLogLevel(): IosLogLevel =
    when (this) {
        LogLevel.VERBOSE -> RCLogLevelVerbose
        LogLevel.DEBUG -> RCLogLevelDebug
        LogLevel.INFO -> RCLogLevelInfo
        LogLevel.WARN -> RCLogLevelWarn
        LogLevel.ERROR -> RCLogLevelError
    }
