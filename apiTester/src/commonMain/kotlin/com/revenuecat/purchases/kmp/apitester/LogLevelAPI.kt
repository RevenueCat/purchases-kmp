package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.LogLevel

@Suppress("unused")
private class LogLevelAPI {
    fun check(logLevel: LogLevel) {
        when (logLevel) {
            LogLevel.VERBOSE,
            LogLevel.DEBUG,
            LogLevel.INFO,
            LogLevel.WARN,
            LogLevel.ERROR,
            -> {
            }
        }.exhaustive
    }
}
