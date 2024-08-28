package com.revenuecat.purchases.kmp.mappings

import android.util.Log
import com.revenuecat.purchases.kmp.LogHandler
import com.revenuecat.purchases.LogHandler as NativeAndroidLogHandler

public fun NativeAndroidLogHandler.toLogHandler(): LogHandler =
    (this as LogHandlerWrapper).wrapped

public fun LogHandler.toAndroidLogHandler(): NativeAndroidLogHandler = LogHandlerWrapper(this)

public class DefaultLogHandler: LogHandler {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        Log.e(tag, msg, throwable)
    }

    override fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    override fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    override fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }
}

private class LogHandlerWrapper(val wrapped: LogHandler) : NativeAndroidLogHandler {
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
