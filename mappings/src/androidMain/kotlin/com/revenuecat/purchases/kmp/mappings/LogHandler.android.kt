package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.LogHandler
import com.revenuecat.purchases.LogHandler as AndroidLogHandler

public fun AndroidLogHandler.toLogHandler(): LogHandler =
    when (this) {
        is LogHandlerWrapper -> wrapped
        else -> AndroidLogHandlerWrapper(this)
    }

public fun LogHandler.toAndroidLogHandler(): AndroidLogHandler =
    when (this) {
        is AndroidLogHandlerWrapper -> wrapped
        else -> LogHandlerWrapper(this)
    }

private class LogHandlerWrapper(val wrapped: LogHandler) : AndroidLogHandler {
    override fun d(tag: String, msg: String) {
        wrapped.d(tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        wrapped.e(tag, msg, throwable)
    }

    override fun i(tag: String, msg: String) {
        wrapped.i(tag, msg)
    }

    override fun v(tag: String, msg: String) {
        wrapped.v(tag, msg)
    }

    override fun w(tag: String, msg: String) {
        wrapped.w(tag, msg)
    }
}

private class AndroidLogHandlerWrapper(val wrapped: AndroidLogHandler) : LogHandler {
    override fun d(tag: String, msg: String) {
        wrapped.d(tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        wrapped.e(tag, msg, throwable)
    }

    override fun i(tag: String, msg: String) {
        wrapped.i(tag, msg)
    }

    override fun v(tag: String, msg: String) {
        wrapped.v(tag, msg)
    }

    override fun w(tag: String, msg: String) {
        wrapped.w(tag, msg)
    }
}
