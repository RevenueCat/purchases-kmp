package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCLogLevel
import cocoapods.PurchasesHybridCommon.RCLogLevelDebug
import cocoapods.PurchasesHybridCommon.RCLogLevelError
import cocoapods.PurchasesHybridCommon.RCLogLevelInfo
import cocoapods.PurchasesHybridCommon.RCLogLevelVerbose
import cocoapods.PurchasesHybridCommon.RCLogLevelWarn

public actual interface LogHandler {
    public actual fun v(tag: String, msg: String)
    public actual fun d(tag: String, msg: String)
    public actual fun i(tag: String, msg: String)
    public actual fun w(tag: String, msg: String)
    public actual fun e(tag: String, msg: String, throwable: Throwable?)
}

internal typealias RcLogHandler = (RCLogLevel, String?) -> Unit

internal fun LogHandler.toRcLogHandler(): RcLogHandler = { logLevel, message ->
    val messageOrLiteralNull = message ?: "null"
    when (logLevel.toLogLevel()) {
        LogLevel.VERBOSE -> v("", messageOrLiteralNull)
        LogLevel.DEBUG -> d("", messageOrLiteralNull)
        LogLevel.INFO -> i("", messageOrLiteralNull)
        LogLevel.WARN -> w("", messageOrLiteralNull)
        LogLevel.ERROR -> e("", messageOrLiteralNull, null)
    }
}

internal fun RcLogHandler.toLogHandler(): LogHandler =
    object : LogHandler {
        override fun v(tag: String, msg: String) = log(RCLogLevelVerbose, tag, msg)
        override fun d(tag: String, msg: String) = log(RCLogLevelDebug, tag, msg)
        override fun i(tag: String, msg: String) = log(RCLogLevelInfo, tag, msg)
        override fun w(tag: String, msg: String) = log(RCLogLevelWarn, tag, msg)
        override fun e(tag: String, msg: String, throwable: Throwable?) =
            log(RCLogLevelError, tag, msg, throwable)

        private fun log(level: RCLogLevel, tag: String, msg: String, throwable: Throwable? = null) =
            this@toLogHandler(
                level,
                buildString {
                    append("[$tag] $msg")
                    throwable?.also { append(" - ${it.message}\n$it") }
                }
            )
    }
