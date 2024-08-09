package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCLogLevel
import cocoapods.PurchasesHybridCommon.RCLogLevelDebug
import cocoapods.PurchasesHybridCommon.RCLogLevelError
import cocoapods.PurchasesHybridCommon.RCLogLevelInfo
import cocoapods.PurchasesHybridCommon.RCLogLevelVerbose
import cocoapods.PurchasesHybridCommon.RCLogLevelWarn

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
