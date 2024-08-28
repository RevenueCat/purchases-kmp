package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCLogLevel
import cocoapods.PurchasesHybridCommon.RCLogLevelDebug
import cocoapods.PurchasesHybridCommon.RCLogLevelError
import cocoapods.PurchasesHybridCommon.RCLogLevelInfo
import cocoapods.PurchasesHybridCommon.RCLogLevelVerbose
import cocoapods.PurchasesHybridCommon.RCLogLevelWarn
import com.revenuecat.purchases.kmp.LogHandler

private typealias IosLogHandler = (RCLogLevel, String?) -> Unit

public fun IosLogHandler.toLogHandler(): LogHandler =
    when (this) {
        is LogHandlerWrapper -> wrapped
        else -> IosLogHandlerWrapper(this)
    }

public fun LogHandler.toIosLogHandler(): IosLogHandler =
    when (this) {
        is IosLogHandlerWrapper -> wrapped
        else -> LogHandlerWrapper(this)
    }

private class LogHandlerWrapper(val wrapped: LogHandler) : IosLogHandler {
    private val tag = "[Purchases]"

    override fun invoke(level: RCLogLevel, message: String?) {
        when (level) {
            RCLogLevelVerbose -> wrapped.v(tag, message ?: "")
            RCLogLevelDebug -> wrapped.d(tag, message ?: "")
            RCLogLevelInfo -> wrapped.i(tag, message ?: "")
            RCLogLevelWarn -> wrapped.w(tag, message ?: "")
            RCLogLevelError -> wrapped.e(tag, message ?: "", null)
        }
    }
}

private class IosLogHandlerWrapper(val wrapped: IosLogHandler) : LogHandler {
    override fun d(tag: String, msg: String) {
        wrapped(RCLogLevelDebug, "[DEBUG][$tag] $msg")
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        wrapped(RCLogLevelError, "[ERROR][$tag] $msg")
        throwable?.printStackTrace()
    }

    override fun i(tag: String, msg: String) {
        wrapped(RCLogLevelInfo, "[INFO][$tag] $msg")
    }

    override fun v(tag: String, msg: String) {
        wrapped(RCLogLevelVerbose, "[VERBOSE][$tag] $msg")
    }

    override fun w(tag: String, msg: String) {
        wrapped(RCLogLevelWarn, "[WARN][$tag] $msg")
    }
}
