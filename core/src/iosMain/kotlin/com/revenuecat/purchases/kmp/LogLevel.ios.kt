package com.revenuecat.purchases.kmp

import swiftPMImport.com.revenuecat.purchases.models.RCLogLevelDebug
import swiftPMImport.com.revenuecat.purchases.models.RCLogLevelError
import swiftPMImport.com.revenuecat.purchases.models.RCLogLevelInfo
import swiftPMImport.com.revenuecat.purchases.models.RCLogLevelVerbose
import swiftPMImport.com.revenuecat.purchases.models.RCLogLevelWarn
import swiftPMImport.com.revenuecat.purchases.models.RCLogLevel as IosLogLevel

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
