package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.LogLevel as AndroidLogLevel

public fun AndroidLogLevel.toLogLevel(): LogLevel =
    when (this) {
        AndroidLogLevel.VERBOSE -> LogLevel.VERBOSE
        AndroidLogLevel.DEBUG -> LogLevel.DEBUG
        AndroidLogLevel.INFO -> LogLevel.INFO
        AndroidLogLevel.WARN -> LogLevel.WARN
        AndroidLogLevel.ERROR -> LogLevel.ERROR
    }

public fun LogLevel.toAndroidLogLevel(): AndroidLogLevel =
    when (this) {
        LogLevel.VERBOSE -> AndroidLogLevel.VERBOSE
        LogLevel.DEBUG -> AndroidLogLevel.DEBUG
        LogLevel.INFO -> AndroidLogLevel.INFO
        LogLevel.WARN -> AndroidLogLevel.WARN
        LogLevel.ERROR -> AndroidLogLevel.ERROR
    }
