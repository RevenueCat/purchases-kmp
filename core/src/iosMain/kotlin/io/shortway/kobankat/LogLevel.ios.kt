package io.shortway.kobankat

import cocoapods.RevenueCat.RCLogLevel
import cocoapods.RevenueCat.RCLogLevelDebug
import cocoapods.RevenueCat.RCLogLevelError
import cocoapods.RevenueCat.RCLogLevelInfo
import cocoapods.RevenueCat.RCLogLevelVerbose
import cocoapods.RevenueCat.RCLogLevelWarn

public actual enum class LogLevel {
    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
}

internal fun RCLogLevel.toLogLevel(): LogLevel =
    when (this) {
        RCLogLevelVerbose -> LogLevel.VERBOSE
        RCLogLevelDebug -> LogLevel.DEBUG
        RCLogLevelInfo -> LogLevel.INFO
        RCLogLevelWarn -> LogLevel.WARN
        RCLogLevelError -> LogLevel.ERROR
        else -> error("Unexpected RCLogLevel: $this")
    }

internal fun LogLevel.toRcLogLevel(): RCLogLevel =
    when (this) {
        LogLevel.VERBOSE -> RCLogLevelVerbose
        LogLevel.DEBUG -> RCLogLevelDebug
        LogLevel.INFO -> RCLogLevelInfo
        LogLevel.WARN -> RCLogLevelWarn
        LogLevel.ERROR -> RCLogLevelError
    }