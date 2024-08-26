package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.LogHandler
import com.revenuecat.purchases.LogHandler as NativeAndroidLogHandler

public fun NativeAndroidLogHandler.toLogHandler(): LogHandler = AndroidLogHandler(this)

public fun LogHandler.toAndroidLogHandler(): NativeAndroidLogHandler = (this as AndroidLogHandler).androidLogHandler

private class AndroidLogHandler(val androidLogHandler: NativeAndroidLogHandler) : LogHandler {
    override fun v(tag: String, msg: String) {
        androidLogHandler.v(tag, msg)
    }

    override fun d(tag: String, msg: String) {
        androidLogHandler.d(tag, msg)
    }

    override fun i(tag: String, msg: String) {
        androidLogHandler.i(tag, msg)
    }

    override fun w(tag: String, msg: String) {
        androidLogHandler.w(tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        androidLogHandler.e(tag, msg, throwable)
    }
}
