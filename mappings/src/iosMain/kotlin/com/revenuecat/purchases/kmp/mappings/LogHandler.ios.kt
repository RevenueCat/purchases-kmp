package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCLogLevel
import cocoapods.PurchasesHybridCommon.RCLogLevelDebug
import cocoapods.PurchasesHybridCommon.RCLogLevelError
import cocoapods.PurchasesHybridCommon.RCLogLevelInfo
import cocoapods.PurchasesHybridCommon.RCLogLevelVerbose
import cocoapods.PurchasesHybridCommon.RCLogLevelWarn
import com.revenuecat.purchases.kmp.LogHandler

private typealias NativeIosLogHandler = (RCLogLevel, String?) -> Unit

public fun LogHandler.toIosLogHandler(): NativeIosLogHandler = {
    level, message -> handleLog(this, level, message)
}

private fun handleLog(logHandler: LogHandler, level: RCLogLevel, message: String?) {
    val tag = "PurchasesKMP"
    when (level) {
        RCLogLevelVerbose -> logHandler.v(tag, message ?: "")
        RCLogLevelDebug -> logHandler.d(tag, message ?: "")
        RCLogLevelInfo -> logHandler.i(tag, message ?: "")
        RCLogLevelWarn -> logHandler.w(tag, message ?: "")
        RCLogLevelError -> logHandler.e(tag, message ?: "", null)
    }
}

public class DefaultLogHandler : LogHandler {
    override fun d(tag: String, msg: String) {
        println("[DEBUG][$tag] $msg")
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        println("[DEBUG][$tag] $msg. Throwable: $throwable")
    }

    override fun i(tag: String, msg: String) {
        println("[INFO][$tag] $msg")
    }

    override fun v(tag: String, msg: String) {
        println("[VERBOSE][$tag] $msg")
    }

    override fun w(tag: String, msg: String) {
        println("[WARN][$tag] $msg")
    }
}
