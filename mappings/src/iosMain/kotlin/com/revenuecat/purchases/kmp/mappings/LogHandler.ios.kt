package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCLogLevel
import cocoapods.PurchasesHybridCommon.RCLogLevelDebug
import cocoapods.PurchasesHybridCommon.RCLogLevelError
import cocoapods.PurchasesHybridCommon.RCLogLevelInfo
import cocoapods.PurchasesHybridCommon.RCLogLevelVerbose
import cocoapods.PurchasesHybridCommon.RCLogLevelWarn
import com.revenuecat.purchases.kmp.LogHandler

private typealias NativeIosLogHandler = (RCLogLevel, String?) -> Unit

public fun NativeIosLogHandler.toLogHandler(): LogHandler = IosLogHandler(this)

public fun LogHandler.toIosLogHandler(): NativeIosLogHandler = (this as IosLogHandler).logHandler

private class IosLogHandler(val logHandler: NativeIosLogHandler) : LogHandler {
    override fun v(tag: String, msg: String) {
        logHandler(RCLogLevelVerbose, msg)
    }

    override fun d(tag: String, msg: String) {
        logHandler(RCLogLevelDebug, msg)
    }

    override fun i(tag: String, msg: String) {
        logHandler(RCLogLevelInfo, msg)
    }

    override fun w(tag: String, msg: String) {
        logHandler(RCLogLevelWarn, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        logHandler(RCLogLevelError, buildString {
            append("[$tag] $msg")
            throwable?.also {
                append(" - ${it.message}\n$it")
            }
        })
    }
}
