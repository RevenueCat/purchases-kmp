package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCLogLevelDebug
import cocoapods.PurchasesHybridCommon.RCLogLevelError
import cocoapods.PurchasesHybridCommon.RCLogLevelInfo
import cocoapods.PurchasesHybridCommon.RCLogLevelVerbose
import cocoapods.PurchasesHybridCommon.RCLogLevelWarn
import com.revenuecat.purchases.kmp.LogLevel
import cocoapods.PurchasesHybridCommon.RCLogLevel as IosLogLevel

public fun IosLogLevel.toLogLevel(): LogLevel =
    when (this) {
        RCLogLevelVerbose -> LogLevel.VERBOSE
        RCLogLevelDebug -> LogLevel.DEBUG
        RCLogLevelInfo -> LogLevel.INFO
        RCLogLevelWarn -> LogLevel.WARN
        RCLogLevelError -> LogLevel.ERROR
        else -> error("Unexpected IosLogLevel: $this")
    }

public fun LogLevel.toRcLogLevel(): IosLogLevel =
    when (this) {
        LogLevel.VERBOSE -> RCLogLevelVerbose
        LogLevel.DEBUG -> RCLogLevelDebug
        LogLevel.INFO -> RCLogLevelInfo
        LogLevel.WARN -> RCLogLevelWarn
        LogLevel.ERROR -> RCLogLevelError
    }
